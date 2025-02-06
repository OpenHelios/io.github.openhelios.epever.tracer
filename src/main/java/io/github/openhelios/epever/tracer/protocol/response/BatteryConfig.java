package io.github.openhelios.epever.tracer.protocol.response;

import io.github.openhelios.epever.tracer.protocol.Data;
import io.github.openhelios.epever.tracer.protocol.Index;

/**
 * The response with battery type data.
 *
 * @param type The battery type.
 * @param capacityInAh The battery capacity in Ah.
 * @param highVoltageDisconnectInV The high disconnect voltage in V.
 * @param chargingLimitVoltageInV The charging limit voltage in V.
 * @param overVoltageReconnectInV The over reconnect voltage in V.
 * @param equalizationVoltageInV The equalization voltage in V.
 */
public record BatteryConfig( //
    BatteryType type, //
    int capacityInAh, //
    float highVoltageDisconnectInV, //
    float chargingLimitVoltageInV, //
    float overVoltageReconnectInV, //
    float equalizationVoltageInV //
) implements Response {

  /**
   * Constructor.
   *
   * @param message The message.
   */
  public BatteryConfig(final byte[] message) {
    this( //
        BatteryType.byId(message[Index.DATA.get()]), //
        Data.uint16(message, Index.DATA.get() + 2), //
        Data.uint16(message, Index.DATA.get() + 6) / 100f, //
        Data.uint16(message, Index.DATA.get() + 8) / 100f, //
        Data.uint16(message, Index.DATA.get() + 10) / 100f, //
        Data.uint16(message, Index.DATA.get() + 12) / 100f //
    );
  }

}
