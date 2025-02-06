package io.github.openhelios.epever.tracer.protocol.response;

import io.github.openhelios.epever.tracer.protocol.Data;
import io.github.openhelios.epever.tracer.protocol.Index;

/**
 * The response with rated data.
 *
 * @param load The array rated.
 * @param batteryTemperatureInC The actual battery temperature in °C.
 * @param insideTemperatureInC The inside temperature in °C.
 * @param powerComponentsTemperatureInC The power components temperature in °C.
 */
public record ActualLoadTemperature( //
    VoltageCurrentPower load, //
    float batteryTemperatureInC, //
    float insideTemperatureInC, //
    float powerComponentsTemperatureInC //
) implements Response {

  /**
   * Constructor.
   *
   * @param message The message.
   */
  public ActualLoadTemperature(final byte[] message) {
    this( //
        new VoltageCurrentPower(message, Index.DATA.get()), // load
        Data.uint16(message, Index.DATA.get() + 8) / 100f, // battery temperature
        Data.uint16(message, Index.DATA.get() + 10) / 100f, // inside temperature
        Data.uint16(message, Index.DATA.get() + 12) / 100f // power temperature
    );
  }

}
