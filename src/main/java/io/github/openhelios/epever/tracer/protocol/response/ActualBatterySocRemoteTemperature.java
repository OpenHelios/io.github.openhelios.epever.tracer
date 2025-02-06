package io.github.openhelios.epever.tracer.protocol.response;

import io.github.openhelios.epever.tracer.protocol.Data;
import io.github.openhelios.epever.tracer.protocol.Index;

/**
 * The response with battery data.
 *
 * @param socInPercent The battery state of charge in percent.
 * @param remoteTemperatureInC The battery remote temperature in °C.
 */
public record ActualBatterySocRemoteTemperature( //
    int socInPercent, //
    float remoteTemperatureInC //
) implements Response {

  /**
   * Constructor.
   *
   * @param message The message.
   */
  public ActualBatterySocRemoteTemperature(final byte[] message) {
    this( //
        Data.uint16(message, Index.DATA.get()), //
        Data.uint16(message, Index.DATA.get() + 2) / 100f //
    );
  }

}
