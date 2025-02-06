package io.github.openhelios.epever.tracer.protocol.response.statistic;

import io.github.openhelios.epever.tracer.protocol.Data;
import io.github.openhelios.epever.tracer.protocol.Index;
import io.github.openhelios.epever.tracer.protocol.response.Response;

/**
 * The response of todays statistics.
 *
 * @param maxArrayVoltageInV The maximum array voltage in V.
 * @param minArrayVoltageInV The minimum array voltage in V.
 * @param maxBatteryVoltageInV The maximum battery voltage in V.
 * @param minBatteryVoltageInV The minimum battery voltage in V.
 */
public record DayMinMaxVoltageStatistics( //
    float maxArrayVoltageInV, //
    float minArrayVoltageInV, //
    float maxBatteryVoltageInV, //
    float minBatteryVoltageInV //
) implements Response {

  /**
   * Constructor.
   *
   * @param message The message.
   */
  public DayMinMaxVoltageStatistics(final byte[] message) {
    this( //
        Data.uint16(message, Index.DATA.get() + 0) / 100f, //
        Data.uint16(message, Index.DATA.get() + 2) / 100f, //
        Data.uint16(message, Index.DATA.get() + 4) / 100f, //
        Data.uint16(message, Index.DATA.get() + 6) / 100f //
    );
  }

}
