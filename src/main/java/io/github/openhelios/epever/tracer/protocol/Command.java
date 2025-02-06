package io.github.openhelios.epever.tracer.protocol;

import io.github.openhelios.epever.tracer.protocol.response.ActualArrayBattery;
import io.github.openhelios.epever.tracer.protocol.response.ActualBatterySocRemoteTemperature;
import io.github.openhelios.epever.tracer.protocol.response.ActualLoadTemperature;
import io.github.openhelios.epever.tracer.protocol.response.BatteryChargingDischargingStatus;
import io.github.openhelios.epever.tracer.protocol.response.BatteryConfig;
import io.github.openhelios.epever.tracer.protocol.response.BatterySystem;
import io.github.openhelios.epever.tracer.protocol.response.Clock;
import io.github.openhelios.epever.tracer.protocol.response.Ok;
import io.github.openhelios.epever.tracer.protocol.response.RatedArrayBatteryChargingMode;
import io.github.openhelios.epever.tracer.protocol.response.RatedLoad;
import io.github.openhelios.epever.tracer.protocol.response.Response;
import io.github.openhelios.epever.tracer.protocol.response.statistic.ArrayEnergyStatistics;
import io.github.openhelios.epever.tracer.protocol.response.statistic.DayMinMaxVoltageStatistics;
import io.github.openhelios.epever.tracer.protocol.response.statistic.LoadEnergyStatistics;

/**
 * Command to set a value, which can be send to the DPS-150.
 */
public class Command implements Message {

  private final byte[] bytes;

  private final Class<? extends Response> responseType;

  /**
   * Constructor for a command.
   *
   * @param functionId The function ID.
   * @param register The register address.
   * @param responseType The expected response type.
   * @param data The pay load with an even number of bytes.
   */
  private Command(final FunctionId functionId, final Register register, final Class<? extends Response> responseType,
      final byte... data) {
    if (0 == data.length) {
      throw new IllegalArgumentException("expected at least two bytes, but size is 0");
    }
    if (0 != (data.length & 1)) {
      throw new IllegalStateException("expected even number of bytes, but was " + Data.hex(data));
    }
    final boolean hasSize = 2 < data.length;
    bytes = new byte[Index.DATA_HIGH.get() + (hasSize ? 3 : 0) + data.length + Checksum.SIZE];
    bytes[Index.DEVICE_ID.get()] = DEFAULT_DEVICE_ID;
    bytes[Index.FUNCTION_ID.get()] = functionId.get();
    bytes[Index.ADDRESS_HIGH.get()] = register.high();
    bytes[Index.ADDRESS_LOW.get()] = register.low();
    int index = Index.DATA_HIGH.get();
    if (hasSize) {
      final int registerCount = data.length / 2;
      bytes[index++] = (byte) (registerCount >> 8);
      bytes[index++] = (byte) (registerCount & 0xFF);
      bytes[index++] = (byte) data.length;
    }
    for (final byte d : data) {
      bytes[index++] = d;
    }
    Checksum.write(bytes);
    this.responseType = responseType;
  }

  /**
   * Constructor for a command.
   *
   * @param functionId The function ID.
   * @param register The register address.
   * @param responseType The expected response type.
   * @param dataUInt16 The register count of type uint16_t.
   */
  private Command(final FunctionId functionId, final Register register, final Class<? extends Response> responseType,
      final int dataUInt16) {
    this(functionId, register, responseType, (byte) (dataUInt16 >> 8), (byte) (dataUInt16 & 0xFF));
  }

  /**
   * Constructor for a command to set a boolean value.
   *
   * @param functionId The function ID.
   * @param registerAddress The register address of type uint16_t.
   * @param isEnabled True to enable.
   * @param responseType The expected response type.
   */
  private Command(final FunctionId functionId, final Register registerAddress, final boolean isEnabled,
      final Class<? extends Response> responseType) {
    this(functionId, registerAddress, responseType, isEnabled ? 0xFF00 : 0x0000);
  }

  /**
   * Constructor for a command to request one value for a given register address.
   *
   * @param functionId The function ID.
   * @param registerAddress The register address of type uint16_t.
   */
  private Command(final FunctionId functionId, final Register registerAddress,
      final Class<? extends Response> responseType) {
    this(functionId, registerAddress, responseType, 1);
  }

  /**
   * Constructor for a command to request values for a given range of register address.
   *
   * @param functionId The function ID.
   * @param beginRegisterAddress The begin register address of type uint16_t.
   * @param endRegisterAddress The end register address of type uint16_t.
   */
  private Command(final FunctionId functionId, final Register beginRegisterAddress, final Register endRegisterAddress,
      final Class<? extends Response> responseType) {
    this(functionId, beginRegisterAddress, responseType, beginRegisterAddress.sizeUpTo(endRegisterAddress));
  }

  @Override
  public byte[] get() {
    return bytes;
  }

  /**
   * The start register address.
   *
   * @return The start register address.
   */
  public Class<? extends Response> getResponseType() {
    return responseType;
  }

  /**
   * Command to request rated data with {@link RatedArrayBatteryChargingMode}.
   *
   * @return The created command.
   */
  public static Command ratedArrayBatteryChargingMode() {
    return new Command(FunctionId.READ_INPUT_REGISTER, Register.ARRAY_RATED_VOLTAGE, Register.CHARGING_MODE,
        RatedArrayBatteryChargingMode.class);
  }

  /**
   * Command to request rated data with {@link RatedLoad}.
   *
   * @return The created command.
   */
  public static Command ratedLoad() {
    return new Command(FunctionId.READ_INPUT_REGISTER, Register.LOAD_RATED_CURRENT, RatedLoad.class);
  }

  /**
   * Command to request actual data with {@link ActualArrayBattery}.
   *
   * @return The created command.
   */
  public static Command actualArrayBattery() {
    return new Command(FunctionId.READ_INPUT_REGISTER, Register.ARRAY_ACTUAL_VOLTAGE,
        Register.BATTERY_ACTUAL_POWER_HIGH, ActualArrayBattery.class);
  }

  /**
   * Command to request actual load, battery temperature and inside temperature with {@link ActualLoadTemperature}.
   *
   * @return The created command.
   */
  public static Command actualLoadTemperature() {
    return new Command(FunctionId.READ_INPUT_REGISTER, Register.LOAD_ACTUAL_VOLTAGE,
        Register.POWER_COMPONENTS_TEMPERATURE, ActualLoadTemperature.class);
  }

  /**
   * Command to request actual battery SOC and remote temperature with {@link ActualBatterySocRemoteTemperature}.
   *
   * @return The created command.
   */
  public static Command actualBatterySocRemoteTemperature() {
    return new Command(FunctionId.READ_INPUT_REGISTER, Register.BATTERY_ACTUAL_SOC,
        Register.REMOTE_BATTERY_ACTUAL_TEMPERATURE, ActualBatterySocRemoteTemperature.class);
  }

  /**
   * Command to request battery system voltage with {@link ActualBatterySocRemoteTemperature}.
   *
   * @return The created command.
   */
  public static Command batterySystem() {
    return new Command(FunctionId.READ_INPUT_REGISTER, Register.BATTERY_SYSTEM_VOLTAGE, BatterySystem.class);
  }

  /**
   * Command to request battery, charging and discharging status with
   * {@link io.github.openhelios.epever.tracer.protocol.response.BatteryChargingDischargingStatus}.
   *
   * @return The created command.
   */
  public static Command batteryChargingDischargingStatus() {
    return new Command(FunctionId.READ_INPUT_REGISTER, Register.BATTERY_STATUS, Register.DISCHARGING_STATUS,
        BatteryChargingDischargingStatus.class);
  }

  /**
   * Command to request battery, charging and discharging status with
   * {@link io.github.openhelios.epever.tracer.protocol.response.BatteryChargingDischargingStatus}.
   *
   * @return The created command.
   */
  public static Command batteryConfig() {
    return new Command(FunctionId.READ_HOLDING_REGISTER, Register.BATTERY_TYPE, Register.DISCHARGING_LIMIT_VOLTAGE,
        BatteryConfig.class);
  }

  /**
   * Command to request battery, charging and discharging status with
   * {@link io.github.openhelios.epever.tracer.protocol.response.BatteryChargingDischargingStatus}.
   *
   * @return The created command.
   */
  public static Command clock() {
    return new Command(FunctionId.READ_HOLDING_REGISTER, Register.CLOCK_SECONDS_MINUTES, Register.CLOCK_MONTH_YEAR,
        Clock.class);
  }

  /**
   * Command to set the real time clock.
   *
   * @param clock The clock.
   * @return The created command.
   */
  public static Command setClock(final Clock clock) {
    return new Command(FunctionId.WRITE_HOLDING_REGISTERS, Register.CLOCK_SECONDS_MINUTES, Ok.class, clock.toData());
  }

  /**
   * Command to set the output load.
   *
   * @param isEnabled True to enable the output load.
   * @return The created command.
   */
  public static Command loadOn(final boolean isEnabled) {
    return new Command(FunctionId.WRITE_COIL, Register.LOAD_ON, isEnabled, Ok.class);
  }

  /**
   * Command to request statistics for the array with
   * {@link io.github.openhelios.epever.tracer.protocol.response.statistic.DayMinMaxVoltageStatistics}.
   *
   * @return The created command.
   */
  public static Command dayMinMaxVoltageStatistics() {
    return new Command(FunctionId.READ_INPUT_REGISTER, Register.DAY_MAX_ARRAY_VOLTAGE, Register.DAY_MIN_BATTERY_VOLTAGE,
        DayMinMaxVoltageStatistics.class);
  }

  /**
   * Command to request statistics for the load energy with
   * {@link io.github.openhelios.epever.tracer.protocol.response.statistic.LoadEnergyStatistics}.
   *
   * @return The created command.
   */
  public static Command loadEnergyStatistics() {
    return new Command(FunctionId.READ_INPUT_REGISTER, Register.DAY_LOAD_ENERGY_LOW, Register.TOTAL_LOAD_ENERGY_HIGH,
        LoadEnergyStatistics.class);
  }

  /**
   * Command to request statistics for the array energy with
   * {@link io.github.openhelios.epever.tracer.protocol.response.statistic.ArrayEnergyStatistics}.
   *
   * @return The created command.
   */
  public static Command arrayEnergyStatistics() {
    return new Command(FunctionId.READ_INPUT_REGISTER, Register.DAY_ARRAY_ENERGY_LOW, Register.TOTAL_ARRAY_ENERGY_HIGH,
        ArrayEnergyStatistics.class);
  }

}
