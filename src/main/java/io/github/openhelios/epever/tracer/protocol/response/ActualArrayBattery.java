package io.github.openhelios.epever.tracer.protocol.response;

import io.github.openhelios.epever.tracer.protocol.Index;

/**
 * The response with rated data.
 *
 * @param array The array rated.
 * @param battery The battery rated.
 */
public record ActualArrayBattery( //
    VoltageCurrentPower array, //
    VoltageCurrentPower battery //
) implements Response {

  /**
   * Constructor.
   *
   * @param message The message.
   */
  public ActualArrayBattery(final byte[] message) {
    this( //
        new VoltageCurrentPower(message, Index.DATA.get()), // array
        new VoltageCurrentPower(message, Index.DATA.get() + 8) // battery
    );
  }

}
