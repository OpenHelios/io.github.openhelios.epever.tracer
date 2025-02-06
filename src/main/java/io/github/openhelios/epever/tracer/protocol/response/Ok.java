package io.github.openhelios.epever.tracer.protocol.response;

import io.github.openhelios.epever.tracer.protocol.FunctionId;
import io.github.openhelios.epever.tracer.protocol.Index;

/**
 * A generic message, which can be used for any response.
 *
 * @param functionId The function ID.
 */
public record Ok(FunctionId functionId) implements Response {

  /**
   * Constructor.
   *
   * @param message The message.
   */
  public Ok(final byte[] message) {
    this(FunctionId.byId(message[Index.FUNCTION_ID.get()]));
  }

  @Override
  public final String toString() {
    return "OK: " + functionId.name();
  }

}
