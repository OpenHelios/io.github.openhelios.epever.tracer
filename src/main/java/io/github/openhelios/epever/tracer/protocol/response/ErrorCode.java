package io.github.openhelios.epever.tracer.protocol.response;

import java.util.HashMap;
import java.util.Map;

/**
 * MODBus error codes.
 *
 * @see <a href=
 *      "https://en.wikipedia.org/wiki/Modbus#Exception_responses">https://en.wikipedia.org/wiki/Modbus#Exception_responses</a>
 */
public enum ErrorCode {

  /** Unknown error code. */
  UNKNOWN_ERROR_CODE,

  /** Unknown function ID. */
  UNKNOWN_FUNCTION_ID,

  /** One or more unknown register address. */
  UNKNOWN_REGISTER_ADDRESS,

  /** Invalid data value send to device. */
  INVALID_DATA_VALUE,

  /** Unrecoverable error on device. */
  UNRECOVERABLE_ERROR_ON_DEVICE,

  /**
   * Request function is currently processed on device. Poll <i>Program Complete message</i> to determine whether
   * processing is completed.
   */
  PROCESSING_FUNCTION_STARTED,

  /** Device is currently busy and can not execute the requested function. */
  DEVICE_BUSY,

  /** Execution on device failed. Send request to get more information. */
  ERROR_EXECUTING_FUNCTION_ON_DEVICE,

  /** Memory parity error on device. */
  READ_ERROR_ON_DEVICE,

  ;

  private static Map<Byte, ErrorCode> map = new HashMap<>();

  static {
    byte code = 0;
    for (final ErrorCode errorCode : values()) {
      map.put(code++, errorCode);
    }
  }

  /**
   * Gets the error code item for the given error code.
   *
   * @param code The error code.
   * @return The error code item, or {@link #UNKNOWN_ERROR_CODE}, if it has not been found.
   */
  public static ErrorCode getByCode(final byte code) {
    final ErrorCode errorCode = map.get(code);
    if (null == errorCode) {
      return UNKNOWN_ERROR_CODE;
    }
    return errorCode;
  }
}
