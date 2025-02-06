package io.github.openhelios.epever.tracer.protocol.response;

import io.github.openhelios.epever.tracer.protocol.FunctionId;

/**
 * An error with function ID and error code from the device.
 *
 * @param functionId The function ID.
 * @param code The error code.
 */
public record Error(FunctionId functionId, ErrorCode code) implements Response {

  /**
   * Constructor.
   *
   * @param bytes The bytes.
   */
  public Error(final byte[] bytes) {
    this(FunctionId.getByIdIgnoringErrorBit(bytes[1]), ErrorCode.getByCode(bytes[2]));
  }

}
