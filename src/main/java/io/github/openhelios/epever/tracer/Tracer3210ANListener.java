package io.github.openhelios.epever.tracer;

import io.github.openhelios.epever.tracer.protocol.response.Response;

/**
 * Listener for the values received from a DPS-150.
 */
public interface Tracer3210ANListener {

  /**
   * A response from the DPS-150 has been received.
   *
   * @param response There are several specific sub types to represent the values in a type safe way.
   */
  void onMessage(Response response);

}
