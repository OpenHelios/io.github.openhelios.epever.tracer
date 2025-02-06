package io.github.openhelios.epever.tracer.protocol.response;

import io.github.openhelios.epever.tracer.protocol.Index;

/**
 * The response with rated data.
 *
 * @param array The array rated.
 * @param battery The battery rated.
 * @param chargingMode The charging mode.
 */
public record RatedArrayBatteryChargingMode( //
    VoltageCurrentPower array, //
    VoltageCurrentPower battery, //
    ChargingMode chargingMode //
) implements Response {

  /**
   * Constructor.
   *
   * @param message The message.
   */
  public RatedArrayBatteryChargingMode(final byte[] message) {
    this( //
        new VoltageCurrentPower(message, Index.DATA.get()), // array
        new VoltageCurrentPower(message, Index.DATA.get() + 8), // battery
        ChargingMode.findById(message[Index.DATA.get() + 17]) //
    );
  }

}
