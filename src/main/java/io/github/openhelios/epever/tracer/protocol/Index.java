package io.github.openhelios.epever.tracer.protocol;

/**
 * Enumeration for the index in a message.
 */
public enum Index {

  /** The index for the device ID, which is the first byte in the message. */
  DEVICE_ID(0),

  /** The index for the function ID, which is the second byte in the message. */
  FUNCTION_ID(1),

  /** The index for the register address high - request only. */
  ADDRESS_HIGH(2),

  /** The index for the register address low - request only. */
  ADDRESS_LOW(3),

  /** The index for the data high byte, which can be for register count high byte or coil value high byte. */
  DATA_HIGH(4),

  /** The index for the data low byte, which can be for register count high byte or coil value high byte. */
  DATA_LOW(5),

  /** The index for the data size of the received message. */
  DATA_SIZE(2),

  /** The index for the first data byte of the received message. */
  DATA(3),

  ;

  private final int index;

  Index(final int index) {
    this.index = index;
  }

  /**
   * The index.
   *
   * @return The index.
   */
  public int get() {
    return index;
  }
}
