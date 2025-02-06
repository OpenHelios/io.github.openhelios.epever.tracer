package io.github.openhelios.epever.tracer.protocol.response;

import io.github.openhelios.epever.tracer.protocol.Data;
import io.github.openhelios.epever.tracer.protocol.Index;

/**
 * The response with battery system data.
 *
 * @param voltageInV The battery system voltage in V.
 */
public record BatterySystem( //
    float voltageInV //
) implements Response {

  /**
   * Constructor.
   *
   * @param message The message.
   */
  public BatterySystem(final byte[] message) {
    this( //
        Data.uint16(message, Index.DATA.get()) / 100f //
    );
  }

}
