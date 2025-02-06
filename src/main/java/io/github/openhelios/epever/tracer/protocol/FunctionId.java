package io.github.openhelios.epever.tracer.protocol;

import java.util.HashMap;
import java.util.Map;

import org.jspecify.annotations.Nullable;

/**
 * Enumeration for function IDs.
 */
public enum FunctionId {

  /** Function ID 0 means unknown. */
  UNKNOWN(0x00),

  /** Read coils. */
  READ_COILS(0x01),

  /** Read discrete inputs. */
  READ_DISCRETE_INPUTS(0x02),

  /** Read holding register. */
  READ_HOLDING_REGISTER(0x03),

  /** Read input register (read only). */
  READ_INPUT_REGISTER(0x04),

  /** Write single coil. */
  WRITE_COIL(0x05),

  /** Write holding register. */
  WRITE_HOLDING_REGISTERS(0x10),

  ;

  private final byte id;

  FunctionId(final int id) {
    this.id = (byte) id;
  }

  /**
   * The byte for the function ID.
   *
   * @return The byte for the function ID.
   */
  public byte get() {
    return id;
  }

  private static final Map<Byte, FunctionId> map;

  static {
    final Map<Byte, FunctionId> m = new HashMap<>();
    for (final FunctionId functionId : values()) {
      m.put(functionId.get(), functionId);
    }
    map = m;
  }

  /**
   * Finds the function ID by the given ID.
   *
   * @param id The function ID.
   * @return The function ID enumeration item, or {@code null}, if it has not been found.
   */
  @Nullable
  public static FunctionId findById(final byte id) {
    return map.get(id);
  }

  /**
   * Gets the function ID by the given ID.
   *
   * @param id The function ID.
   * @return The function ID enumeration item.
   * @throws IllegalArgumentException if the given ID has not been found.
   */
  public static FunctionId byId(final byte id) {
    @Nullable
    final FunctionId functionId = findById(id);
    if (null == functionId) {
      throw new IllegalArgumentException("unknown function ID " + Data.hex(id));
    }
    return functionId;
  }

  /**
   * Finds the function ID by the given ID ignoring the error bit, which is the highest bit.
   *
   * @param id The function ID.
   * @return The function ID enumeration item. May be {@link #UNKNOWN}, if not found.
   */
  public static FunctionId getByIdIgnoringErrorBit(final byte id) {
    @Nullable
    final FunctionId result = findById((byte) (id & 0x7F));
    if (null == result) {
      return UNKNOWN;
    }
    return result;
  }

  /**
   * Checks, if the function ID is an error response.
   *
   * @param id The function ID.
   * @return True, if the function ID is an error response.
   */
  public static boolean hasErrorBit(final byte id) {
    return 0 != (id & 0x80);
  }
}
