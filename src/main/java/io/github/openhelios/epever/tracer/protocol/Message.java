package io.github.openhelios.epever.tracer.protocol;

/**
 * Interface for a message.
 */
public interface Message {

  /** The default device ID, which is 1 for an EPEVER Tracer. */
  byte DEFAULT_DEVICE_ID = 1;

  /**
   * Returns the message bytes.
   *
   * @return The bytes of the message.
   */
  byte[] get();

}
