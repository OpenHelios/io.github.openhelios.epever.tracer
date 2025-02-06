package io.github.openhelios.epever.tracer.protocol.response;

import java.util.HashMap;
import java.util.Map;

import org.jspecify.annotations.Nullable;

/**
 * The charging modes.
 */
public enum ChargingMode {

  /** Direct connect / disconnect. */
  DIRECT,

  /** Pulse With Modulation. */
  PWM,

  /** Maximum Power Point Tracking. */
  MPPT,

  ;

  private static Map<Byte, ChargingMode> map = new HashMap<>();

  static {
    byte i = 0;
    for (final ChargingMode chargingMode : values()) {
      map.put(i++, chargingMode);
    }
  }

  /**
   * Finds the charging mode by ID.
   *
   * @param id The charging mode ID.
   * @return The charging mode for the given ID.; may be {@code null}, if not found.
   */
  @Nullable
  public static ChargingMode findById(final byte id) {
    return map.get(id);
  }

}
