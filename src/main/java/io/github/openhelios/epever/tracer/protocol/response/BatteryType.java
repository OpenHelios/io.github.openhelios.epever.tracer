package io.github.openhelios.epever.tracer.protocol.response;

import java.util.HashMap;
import java.util.Map;

/**
 * The battery type.
 */
public enum BatteryType {

  /** User defined. */
  USER_DEFINED,

  /** Sealed. */
  SEALED,

  /** Gel. */
  GEL,

  /** Flooded. */
  FLOODED,

  ;

  private static final Map<Byte, BatteryType> map = new HashMap<>();

  static {
    byte i = 0;
    for (final BatteryType batteryType : values()) {
      map.put(i++, batteryType);
    }
  }

  /**
   * Gets the battery type by ID.
   *
   * @param id The battery type ID.
   * @return The enumeration item.
   */
  public static BatteryType byId(final byte id) {
    final BatteryType type = map.get(id);
    if (null == type) {
      throw new IllegalStateException("id " + id + " not found");
    }
    return type;
  }

}
