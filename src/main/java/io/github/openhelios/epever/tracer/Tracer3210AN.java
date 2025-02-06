package io.github.openhelios.epever.tracer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import io.github.openhelios.epever.tracer.protocol.Command;
import io.github.openhelios.epever.tracer.protocol.Data;
import io.github.openhelios.epever.tracer.protocol.response.Clock;
import io.github.openhelios.epever.tracer.protocol.response.Messages;
import io.github.openhelios.epever.tracer.protocol.response.Response;

/**
 * Represents the Tracer 3210AN to send commands to and receive responses.
 */
public class Tracer3210AN implements SerialPortDataListener, AutoCloseable {

  private static final String DESCRIPTIVE_PORT_NAME = "USB Single Serial";

  private static final Logger LOG = LoggerFactory.getLogger(Tracer3210AN.class);

  private final SerialPort serialPort;

  private final Set<Tracer3210ANListener> listeners = new HashSet<>();

  private Class<? extends Response> expectedResponseType;

  private boolean isDebugOn;

  private static SerialPort getSerialPortByDescrptvePortName(final SerialPort[] serialPorts) {
    final List<String> names = new ArrayList<>(serialPorts.length);
    for (final SerialPort serialPort : serialPorts) {
      final String name = serialPort.getDescriptivePortName();
      if (DESCRIPTIVE_PORT_NAME.equals(name)) {
        return serialPort;
      }
      names.add(name);
    }
    throw new IllegalStateException(DESCRIPTIVE_PORT_NAME + " not found in: " + names);
  }

  /**
   * Opens a serial connection with a Tracer 3210AN.
   *
   * @param serialPort The serial port to be used to open the connection with DPS-150.
   */
  public Tracer3210AN(final SerialPort serialPort) {
    this.serialPort = serialPort;
    serialPort.setBaudRate(115200);
    serialPort.setNumDataBits(8);
    serialPort.setNumStopBits(1);
    serialPort.setParity(SerialPort.NO_PARITY);
    serialPort.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
    if (!serialPort.openPort()) {
      final int code = serialPort.getLastErrorCode();
      throw new IllegalStateException(
          "opening commPort " + serialPort.getDescriptivePortName() + " failed: error code " + code
              + (11 == code ? " (opened by an other process?)" : "") + ", line " + serialPort.getLastErrorLocation());
    }
    SerialPort.addShutdownHook(new Thread(this::onShutdown));
  }

  /**
   * Default constructor searches automatically for DPS-150.
   */
  public Tracer3210AN() {
    this(getSerialPortByDescrptvePortName(SerialPort.getCommPorts()));
  }

  /**
   * Adds a listener, which wants to be informed by each received response from DPS-150.
   *
   * @param listener The DPS-150 listener.
   */
  public void addListener(final Tracer3210ANListener listener) {
    this.listeners.add(listener);
    if (1 == listeners.size()) {
      serialPort.addDataListener(this);
    }
  }

  /**
   * Removes the given previously added listener.
   *
   * @param listener The listener.
   */
  public void removeListener(final Tracer3210ANListener listener) {
    if (listeners.remove(listener) && listeners.isEmpty()) {
      serialPort.removeDataListener();
    }
  }

  /**
   * Removes all previously added listeners.
   */
  public void removeAllListeners() {
    for (final var listener : new ArrayList<>(listeners)) {
      removeListener(listener);
    }
  }

  /**
   * Gets the debug mode.
   *
   * @return True for debug mode on.
   */
  public boolean isDebugOn() {
    return isDebugOn;
  }

  /**
   * Sets the debug mode on and off for printing the raw output and input bytes.
   *
   * @param isEnabled True to enable.
   */
  public void setDebugOn(final boolean isEnabled) {
    this.isDebugOn = isEnabled;
  }

  private void writeBytes(final byte[] bytes) {
    if (isDebugOn) {
      ConsolePrinter.println("writeBytes: " + Data.hex(bytes));
    }
    final int count = serialPort.writeBytes(bytes, bytes.length);
    if (count != bytes.length) {
      throw new IllegalStateException("expected " + bytes.length + " send bytes, but returned " + count);
    }
  }

  private void writeBytes(final Command command) {
    expectedResponseType = command.getResponseType();
    writeBytes(command.get());
    try {
      Thread.sleep(75);
    } catch (final InterruptedException e) {
      // ignore
    }
  }

  /**
   * Requests {@link io.github.openhelios.epever.tracer.protocol.response.RatedArrayBatteryChargingMode}.
   */
  public void requestRatedArrayBatteryChargingMode() {
    writeBytes(Command.ratedArrayBatteryChargingMode());
  }

  /**
   * Requests {@link io.github.openhelios.epever.tracer.protocol.response.RatedLoad}.
   */
  public void requestRatedLoad() {
    writeBytes(Command.ratedLoad());
  }

  /**
   * Requests {@link io.github.openhelios.epever.tracer.protocol.response.ActualArrayBattery}.
   */
  public void requestActualArrayBattery() {
    writeBytes(Command.actualArrayBattery());
  }

  /**
   * Requests {@link io.github.openhelios.epever.tracer.protocol.response.ActualLoadTemperature}.
   */
  public void requestActualLoadTemperature() {
    writeBytes(Command.actualLoadTemperature());
  }

  /**
   * Requests {@link io.github.openhelios.epever.tracer.protocol.response.ActualBatterySocRemoteTemperature}.
   */
  public void requestActualBatterySocRemoteTemperature() {
    writeBytes(Command.actualBatterySocRemoteTemperature());
  }

  /**
   * Requests {@link io.github.openhelios.epever.tracer.protocol.response.BatterySystem}.
   */
  public void requestBatterySystem() {
    writeBytes(Command.batterySystem());
  }

  /**
   * Requests {@link io.github.openhelios.epever.tracer.protocol.response.BatteryChargingDischargingStatus}.
   */
  public void requestBatteryChargingDischargingStatus() {
    writeBytes(Command.batteryChargingDischargingStatus());
  }

  /**
   * Requests {@link io.github.openhelios.epever.tracer.protocol.response.BatteryConfig}.
   */
  public void requestBatteryConfig() {
    writeBytes(Command.batteryConfig());
  }

  /**
   * Requests {@link io.github.openhelios.epever.tracer.protocol.response.Clock}.
   */
  public void requestClock() {
    writeBytes(Command.clock());
  }

  /**
   * Sets the real time clock to the given time.
   *
   * @param clock The clock.
   */
  public void setClock(final Clock clock) {
    writeBytes(Command.setClock(clock));
  }

  /**
   * Command to set the output load.
   *
   * @param isEnabled True to enable the output load.
   */
  public void setOutputLoadOn(final boolean isEnabled) {
    writeBytes(Command.loadOn(isEnabled));
  }

  /**
   * Requests {@link io.github.openhelios.epever.tracer.protocol.response.statistic.DayMinMaxVoltageStatistics}.
   */
  public void requestDayMinMaxVoltageStatistics() {
    writeBytes(Command.dayMinMaxVoltageStatistics());
  }

  /**
   * Requests {@link io.github.openhelios.epever.tracer.protocol.response.statistic.LoadEnergyStatistics}.
   */
  public void requestLoadEnergyStatistics() {
    writeBytes(Command.loadEnergyStatistics());
  }

  /**
   * Requests {@link io.github.openhelios.epever.tracer.protocol.response.statistic.ArrayEnergyStatistics}.
   */
  public void requestArrayEnergyStatistics() {
    writeBytes(Command.arrayEnergyStatistics());
  }

  /**
   * The connected state.
   *
   * @return True, if the serial port is currently connected.
   */
  public boolean isConnected() {
    return serialPort.isOpen();
  }

  @Override
  public void close() {
    if (serialPort.isOpen()) {
      serialPort.flushDataListener();
      serialPort.closePort();
    }
  }

  @Override
  public int getListeningEvents() {
    return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
  }

  @Override
  public void serialEvent(final SerialPortEvent event) {
    if (listeners.isEmpty()) {
      LOG.info("no listener registered");
      return;
    }
    final byte[] data = event.getReceivedData();
    if (isDebugOn) {
      ConsolePrinter.println("readBytes : " + Data.hex(data));
    }
    final Messages messages = new Messages(data, expectedResponseType, listeners);
    new Thread(messages::readAll).start();
  }

  private void onShutdown() {
    close();
    removeAllListeners();
  }

}
