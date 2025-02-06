package io.github.openhelios.epever.tracer.protocol.response;

import io.github.openhelios.epever.tracer.protocol.Data;
import io.github.openhelios.epever.tracer.protocol.Index;

/**
 * The response with rated data.
 *
 * @param currentInA The load rated current in A.
 */
public record RatedLoad( //
    float currentInA //
) implements Response {

  /**
   * Constructor.
   *
   * @param message The message.
   */
  public RatedLoad(final byte[] message) {
    this( //
        Data.uint16(message, Index.DATA.get()) / 100f //
    );
  }

}
