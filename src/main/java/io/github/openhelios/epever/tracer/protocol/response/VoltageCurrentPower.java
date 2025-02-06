package io.github.openhelios.epever.tracer.protocol.response;

import io.github.openhelios.epever.tracer.protocol.Data;

/**
 * The voltage, current and power.
 *
 * @param voltageInV The voltage in V.
 * @param currentInA The current in A.
 * @param powerInW The power in W.
 */
public record VoltageCurrentPower( //
    float voltageInV, //
    float currentInA, //
    double powerInW) implements Response {

  /**
   * Constructor.
   *
   * @param message The message.
   * @param index The start index.
   */
  public VoltageCurrentPower(final byte[] message, final int index) {
    this( //
        Data.uint16(message, index) / 100f, //
        Data.uint16(message, index + 2) / 100f, //
        Data.uint32(message, index + 4) / 100.0);
  }

}
