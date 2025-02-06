package io.github.openhelios.epever.tracer.protocol.response;

import io.github.openhelios.epever.tracer.protocol.Data;

/**
 * A generic message, which can be used for any response.
 *
 * @param message The bytes.
 */
public record GenericResponse(byte[] message) implements Response {

  @Override
  public final String toString() {
    return Data.hex(message);
  }

}
