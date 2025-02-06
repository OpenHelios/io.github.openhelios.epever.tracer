package io.github.openhelios.epever.tracer.protocol.response.statistic;

import io.github.openhelios.epever.tracer.protocol.Data;
import io.github.openhelios.epever.tracer.protocol.Index;
import io.github.openhelios.epever.tracer.protocol.response.Response;

/**
 * The response of load energy statistics.
 *
 * @param dayInKWh Load energy this day in kWh.
 * @param monthInKWh Load energy this month in kWh.
 * @param yearInKWh Load energy this year in kWh.
 * @param totalInKWh Load energy totally in kWh.
 */
public record LoadEnergyStatistics( //
    float dayInkWh, //
    float monthInkWh, //
    float yearInkWh, //
    float totalInkWh //
) implements Response {

  /**
   * Constructor.
   *
   * @param message The message.
   */
  public LoadEnergyStatistics(final byte[] message) {
    this( //
        Data.uint32(message, Index.DATA.get() + 0) / 100f, //
        Data.uint32(message, Index.DATA.get() + 4) / 100f, //
        Data.uint32(message, Index.DATA.get() + 8) / 100f, //
        Data.uint32(message, Index.DATA.get() + 12) / 100f //
    );
  }

}
