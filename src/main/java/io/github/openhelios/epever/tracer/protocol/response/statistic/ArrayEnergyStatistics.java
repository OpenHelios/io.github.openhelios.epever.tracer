package io.github.openhelios.epever.tracer.protocol.response.statistic;

import io.github.openhelios.epever.tracer.protocol.Data;
import io.github.openhelios.epever.tracer.protocol.Index;
import io.github.openhelios.epever.tracer.protocol.response.Response;

/**
 * The response of array energy statistics.
 *
 * @param dayInKWh Array energy this day in kWh.
 * @param monthInKWh Array energy this month in kWh.
 * @param yearInKWh Array energy this year in kWh.
 * @param totalInKWh Array energy totally in kWh.
 */
public record ArrayEnergyStatistics( //
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
  public ArrayEnergyStatistics(final byte[] message) {
    this( //
        Data.uint32(message, Index.DATA.get() + 0) / 100f, //
        Data.uint32(message, Index.DATA.get() + 4) / 100f, //
        Data.uint32(message, Index.DATA.get() + 8) / 100f, //
        Data.uint32(message, Index.DATA.get() + 12) / 100f //
    );
  }

}
