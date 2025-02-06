package io.github.openhelios.epever.tracer.protocol;

/**
 * Register address represented by high and low byte.
 */
public enum Register {

  // *** COIL {@link FunctionId#READ_COILS} and {@link FunctionId#WRITE_COIL}.

  /** Charging On: 0=No, 1=Yes. */
  CHARGING_ON(0x00),

  /** Manual load mode on: 0=No, 1=Yes */
  MANUAL_LOAD_MODE_ON(0x01),

  /** Load on while in manual load mode: 0=No, 1=Yes. */
  LOAD_ON(0x02),

  /** Default load on: 0=No, 1=Yes. */
  DEFAULT_LOAD_ON(0x03),

  /** Test load mode on: 0=No, 1=Yes. */
  TEST_LOAD_MODE_ON(0x05),

  /** Force load on: 0=No, 1=Yes. */
  FORCE_LOAD_ON(0x06),

  /** Restore system defaults: 0=No, 1=Yes. */
  RESTORE_SYSTEM_DEFAULTS(0x13),

  /** Clear statistics with root privileges: 0=No, 1=Yes. */
  CLEAR_STATISTICS(0x14),

  // *** Discrete value {@link FunctionId#READ_DISCRETE_INPUTS}.

  /** Inside temperature is over protection point: 0=No, 1=Yes. */
  OVER_INSIDE_TEMPERATURE(0x2000),

  /** Is night mode active: 0=No, 1=Yes. */
  IS_NIGHT(0x200C),

  // *** Rated data {@link FunctionId#READ_INPUT_REGISTER}.

  /** PV array rated voltage in 100V. */
  ARRAY_RATED_VOLTAGE(0x3000),

  /** PV array rated current in 100A. */
  ARRAY_RATED_CURRENT(0x3001),

  /** PV array rated power in 100W low 16 bits. */
  ARRAY_RATED_POWER_LOW(0x3002),

  /** PV array rated power in 100W high 16 bits. */
  ARRAY_RATED_POWER_HIGH(0x3003),

  /** Battery rated voltage in 100V. */
  BATTERY_RATED_VOLTAGE(0x3004),

  /** Battery rated current in 100A. */
  BATTERY_RATED_CURRENT(0x3005),

  /** Battery rated power in 100W low 16 bits. */
  BATTERY_RATED_POWER_LOW(0x3006),

  /** Battery power in 100W high 16 bits. */
  BATTERY_RATED_POWER_HIGH(0x3007),

  /** Charging mode: 0=connected/disconnected, 1=PWM, 2=MPPT. */
  CHARGING_MODE(0x3008),

  /** Load rated current in 100A. */
  LOAD_RATED_CURRENT(0x300E),

  // *** Real time data {@link FunctionId#READ_INPUT_REGISTER}.

  /** PV array actual voltage in 100V. */
  ARRAY_ACTUAL_VOLTAGE(0x3100),

  /** PV array actual current in 100A. */
  ARRAY_ACTUAL_CURRENT(0x3101),

  /** PV array actual power in 100W low 16 bits. */
  ARRAY_ACTUAL_POWER_LOW(0x3102),

  /** PV array actual power in 100W high 16 bits. */
  ARRAY_ACTUAL_POWER_HIGH(0x3103),

  /** Battery actual voltage in 100V. */
  BATTERY_ACTUAL_VOLTAGE(0x3104),

  /** Battery actual current in 100A. */
  BATTERY_ACTUAL_CURRENT(0x3105),

  /** Battery actual power in 100W low 16 bits. */
  BATTERY_ACTUAL_POWER_LOW(0x3106),

  /** Battery actual power in 100W high 16 bits. */
  BATTERY_ACTUAL_POWER_HIGH(0x3107),

  /** Load actual voltage in 100V. */
  LOAD_ACTUAL_VOLTAGE(0x310C),

  /** Load actual current in 100A. */
  LOAD_ACTUAL_CURRENT(0x310D),

  /** Load actual power in 100W low 16 bits. */
  LOAD_ACTUAL_POWER_LOW(0x310E),

  /** Load actual power in 100W high 16 bits. */
  LOAD_ACTUAL_POWER_HIGH(0x310F),

  /** Battery actual temperature in 100°C. */
  BATTERY_ACTUAL_TEMPERATURE(0x3110),

  /** Inside actual temperature in 100°C. */
  INSIDE_ACTUAL_TEMPERATURE(0x3111),

  /** Power components actual temperature in 100°C. */
  POWER_COMPONENTS_TEMPERATURE(0x3112),

  /** Actual percentage of the remaining battery capacity in 100%. */
  BATTERY_ACTUAL_SOC(0x311A),

  /** Remote battery actual temperature in 100°C. */
  REMOTE_BATTERY_ACTUAL_TEMPERATURE(0x311B),

  /** Battery system voltage in 100V (can be 12V, 24V, 36V or 48V). */
  BATTERY_SYSTEM_VOLTAGE(0x311D),

  // *** Battery status {@link FunctionId#READ_INPUT_REGISTER}

  /**
   * <ul>
   * <li>0-3: Voltage 0=Normal, 1=Over, 2=Under, 3=Low disconnected, 4=Fault
   * <li>4-7: Temperature 0=Normal, 1=Over, 2=Under
   * <li>8: Battery inner resistance: 0=Normal, 1=Fault
   * <li>15: Battery system voltage: 0=Normal, 1=Wrong identification
   * </ul>
   */
  BATTERY_STATUS(0x3200),

  /**
   * <ul>
   * <li>0: Charging On: 0=Standby, 1=Running
   * <li>1: Error: 0=Normal, 1=Fault
   * <li>2-3: Charging mode: 0=Off, 1=Float, 2=Boost, 3=Equalization
   * <li>4: input short circuit: 0=No, 1=Yes
   * <li>7: MOSFET short circuit: 0=No, 1=Yes
   * <li>8: Load short circuit: 0=No, 1=Yes
   * <li>9: Load over current: 0=No, 1=Yes
   * <li>10: Input is over current: 0=No, 1=Yes
   * <li>11: Anti-reverse MOSFET short circuit: 0=No, 1=Yes
   * <li>12: Charging or Anit-reverse MOSFET short circuit: 0=No, 1=Yes
   * <li>13: Charging MOSFET is short circuit: 0=No, 1=Yes
   * <li>14-15: Input voltage: 0=Normal, 1=Disconnected, 2=Over, 3=Fault
   * </ul>
   */
  CHARGING_STATUS(0x3201),

  /**
   * <ul>
   * <li>0: Discharging On: 0=Standby, 1=Running
   * <li>1: Error: 0=Normal, 1=Fault
   * <li>4: output over current: 0=No, 1=Yes
   * <li>5: Boost over current: 0=No, 1=Yes
   * <li>6: voltage short circuit: 0=No, 1=Yes
   * <li>7: output over current: 0=No, 1=Yes
   * <li>8: output voltage abnormal: 0=No, 1=Yes
   * <li>9: unable to stop input: 0=No, 1=Yes
   * <li>10: unable to discharge: 0=No, 1=Yes
   * <li>11: short circuit: 0=No, 1=Yes
   * <li>12-13: output power: 0=light, 1=moderate, 2=rated, 3=overload
   * <li>14-15: Input voltage: 0=Normal, 1=low, 2=high, 3=Fault
   * </ul>
   */
  DISCHARGING_STATUS(0x3202),

  // *** Statistical Parameters {@link FunctionId#READ_INPUT_REGISTER}

  /** Todays maximum array voltage in 100V. */
  DAY_MAX_ARRAY_VOLTAGE(0x3300),

  /** Todays minimum array voltage in 100V. */
  DAY_MIN_ARRAY_VOLTAGE(0x3301),

  /** Todays maximum battery voltage in 100V. */
  DAY_MAX_BATTERY_VOLTAGE(0x3302),

  /** Todays minimum battery voltage in 100V. */
  DAY_MIN_BATTERY_VOLTAGE(0x3303),

  /** Todays load energy low bytes in 100kWh. */
  DAY_LOAD_ENERGY_LOW(0x3304),

  /** Todays load energy high bytes in 100kWh. */
  DAY_LOAD_ENERGY_HIGH(0x3305),

  /** Month load energy low bytes in 100kWh. */
  MONTH_LOAD_ENERGY_LOW(0x3306),

  /** Month load energy high bytes in 100kWh. */
  MONTH_LOAD_ENERGY_HIGH(0x3307),

  /** Year load energy low bytes in 100kWh. */
  YEAR_LOAD_ENERGY_LOW(0x3308),

  /** Year load energy high bytes in 100kWh. */
  YEAR_LOAD_ENERGY_HIGH(0x3309),

  /** Year load energy low bytes in 100kWh. */
  TOTAL_LOAD_ENERGY_LOW(0x330A),

  /** Year load energy high bytes in 100kWh. */
  TOTAL_LOAD_ENERGY_HIGH(0x330B),

  /** Todays array energy low bytes in 100kWh. */
  DAY_ARRAY_ENERGY_LOW(0x330C),

  /** Todays array energy high bytes in 100kWh. */
  DAY_ARRAY_ENERGY_HIGH(0x330D),

  /** Month array energy low bytes in 100kWh. */
  MONTH_ARRAY_ENERGY_LOW(0x330E),

  /** Month array energy high bytes in 100kWh. */
  MONTH_ARRAY_ENERGY_HIGH(0x330F),

  /** Year array energy low bytes in 100kWh. */
  YEAR_ARRAY_ENERGY_LOW(0x3310),

  /** Year array energy high bytes in 100kWh. */
  YEAR_ARRAY_ENERGY_HIGH(0x3311),

  /** Year array energy low bytes in 100kWh. */
  TOTAL_ARRAY_ENERGY_LOW(0x3312),

  /** Year array energy high bytes in 100kWh. */
  TOTAL_ARRAY_ENERGY_HIGH(0x3313),

  // *** Holding Registers {@link FunctionId#READ_HOLDING_REGISTER} and {@link FunctionId#WRITE_HOLDING_REGISTERS}

  /** Battery type: 0=User, 1=Sealed, 2=GEL, 3=Flooded. */
  BATTERY_TYPE(0x9000),

  /** Battery capacity in Ah. */
  BATTERY_CAPACITY(0x9001),

  /** Discharging limit voltage in V. */
  DISCHARGING_LIMIT_VOLTAGE(0x900E),

  /**
   * Clock seconds and minutes:
   * <ul>
   * <li>0-7: seconds 0-59
   * <li>8-15: minutes 0-59
   * </ul>
   */
  CLOCK_SECONDS_MINUTES(0x9013),

  /**
   * Clock hour and day:
   * <ul>
   * <li>0-7: hour 0-23
   * <li>8-15: day 1-31
   * </ul>
   */
  CLOCK_HOUR_DAY(0x9014),

  /**
   * Clock month and year:
   * <ul>
   * <li>0-7: month 1-12
   * <li>8-15: year 0-255 (0=2000, ..., 255=2255)
   * </ul>
   */
  CLOCK_MONTH_YEAR(0x9015),

  ;

  private final int address;

  private final byte high;

  private final byte low;

  Register(final int address) {
    this.address = address;
    high = (byte) (address >> 8);
    low = (byte) (address & 0xFF);
  }

  /**
   * The high byte of the register address.
   *
   * @return The high byte of the register address.
   */
  public byte high() {
    return high;
  }

  /**
   * The low byte of the register address.
   *
   * @return The low byte of the register address.
   */
  public byte low() {
    return low;
  }

  /**
   * Calculates the size between this and the the oder register address including both.
   *
   * @param other The other register.
   * @return The size between this and the the oder register address including both.
   */
  public int sizeUpTo(final Register other) {
    return other.address - address + 1;
  }
}
