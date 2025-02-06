package io.github.openhelios.epever.tracer;

import java.io.IOException;

import io.github.openhelios.epever.tracer.protocol.response.BatteryChargingDischargingStatus;
import io.github.openhelios.epever.tracer.protocol.response.Clock;
import io.github.openhelios.epever.tracer.protocol.response.Response;

/**
 * Example console program for the usage of the Tracer 3210AN class.
 *
 * Sends commands to and receives responses from the DPS-150
 */
public class Main {

  private final Tracer3210AN device;

  private boolean isOutputLoadOn;

  /**
   * The main method.
   *
   * @param args The command line arguments, which are not used.
   * @throws IOException If there is an IO exception.
   */
  public static void main(final String[] args) throws IOException {
    final Main main = new Main(new Tracer3210AN());
    main.start();
  }

  private Main(final Tracer3210AN device) {
    this.device = device;
  }

  private void printHelp() {
    System.out.println("""
        r Request all values (array, battery, load)
        s Request statistics (day, load)
        c Request clock
        u Update the clock with the current date time
        o Switch output load on / off
        d Switch debug of raw bytes on / off
        q Quit application
        h Print this help
        """);
  }

  private void start() throws IOException {
    final ConsolePrinter consolePrinter = new ConsolePrinter();
    device.addListener(consolePrinter);
    device.addListener(this::onMessage);
    requestAllValues();
    boolean isRunning = true;
    while (isRunning) {
      final char c = (char) System.in.read();
      switch (c) {
        case 'r' -> requestAllValues();
        case 's' -> requestStatistics();
        case 'c' -> device.requestClock();
        case 'u' -> device.setClock(new Clock());
        case 'o' -> switchOutputLoadOn();
        case 'd' -> switchDebugOn();
        case 'h' -> printHelp();
        case 'q' -> isRunning = false;
        case '\n' -> {
        }
        default -> ConsolePrinter.println("No action defined for key: " + c);
      }
    }
    device.close();
    device.removeAllListeners();
  }

  private void switchDebugOn() {
    final boolean isEnabled = !device.isDebugOn();
    ConsolePrinter.println("Set debugOn=" + isEnabled);
    device.setDebugOn(isEnabled);
  }

  private void requestAllValues() {
    device.requestRatedArrayBatteryChargingMode();
    device.requestRatedLoad();
    device.requestActualArrayBattery();
    device.requestActualLoadTemperature();
    device.requestActualBatterySocRemoteTemperature();
    device.requestBatterySystem();
    device.requestBatteryConfig();
    device.requestClock();
    device.requestBatteryChargingDischargingStatus();
  }

  private void requestStatistics() {
    device.requestDayMinMaxVoltageStatistics();
    device.requestLoadEnergyStatistics();
    device.requestArrayEnergyStatistics();
  }

  private void switchOutputLoadOn() {
    device.setOutputLoadOn(!isOutputLoadOn);
    device.requestBatteryChargingDischargingStatus();
  }

  private void onMessage(final Response response) {
    switch (response) {
      case final BatteryChargingDischargingStatus l -> isOutputLoadOn = l.isOutputLoadOn();
      default -> {
      }
    }
  }

}
