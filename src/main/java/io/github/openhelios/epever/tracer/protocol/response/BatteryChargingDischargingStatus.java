package io.github.openhelios.epever.tracer.protocol.response;

import io.github.openhelios.epever.tracer.protocol.Index;

/**
 * The response with battery, charing and discharging status.
 *
 * @param isOutputLoadOn True, if load is enabled.
 */
public record BatteryChargingDischargingStatus( //
    boolean isOutputLoadOn //
) implements Response {
  /**
   * Constructor.
   *
   * @param message The message.
   */
  public BatteryChargingDischargingStatus(final byte[] message) {
    this(1 == (message[Index.DATA.get() + 5] & 1));
  }

}
