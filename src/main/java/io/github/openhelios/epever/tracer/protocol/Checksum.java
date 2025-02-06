package io.github.openhelios.epever.tracer.protocol;

import org.jspecify.annotations.Nullable;

/**
 * Utility class to generate and check the checksum, which is CRC.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Modbus#Modbus_RTU">https://en.wikipedia.org/wiki/Modbus#Modbus_RTU</a>
 */
public class Checksum {

  /** The amount of bytes used for the checksum, which is 2. */
  public static final int SIZE = 2;

  private static int generate(final byte[] buffer) {
    int crc = 0xFFFF; // highest number of uint32_t
    for (int i = 0; i < buffer.length - 2; i++) {
      crc ^= buffer[i] & 0xFF; // XOR each byte into CRC
      for (int bit = 0; bit < 8; bit++) {
        final boolean isLastBitSet = 0 != (crc & 1);
        crc >>= 1; // one bit shift right
        if (isLastBitSet) { // last bit is set
          crc ^= 0xA001; // XOR 0xA001
        }
      }
    }
    return crc; // low and high byte is swapped
  }

  /**
   * Generates a checksum from the given bytes and writes into the last byte.
   *
   * @param bytes The message.
   */
  public static void write(final byte[] bytes) {
    final int crc = generate(bytes);
    bytes[bytes.length - 2] = (byte) (crc & 0xFF);
    bytes[bytes.length - 1] = (byte) (crc >> 8);
  }

  /**
   * Verifies the checksum in the given message.
   *
   * @param bytes The message.
   * @return Empty string for a valid checksum, otherwise an error message.
   */
  @Nullable
  public static String check(final byte[] bytes) {
    final int generatedCRC = generate(bytes);
    final int crc = (bytes[bytes.length - 1] & 0xFF) << 8 | bytes[bytes.length - 2] & 0xFF;
    if (crc != generatedCRC) {
      return "expected checksum " + Data.hexUInt32(generatedCRC) + ", but was " + Data.hexUInt32(crc) + " in "
          + Data.hex(bytes);
    }
    return null;
  }

  private Checksum() {
    // hide
  }
}
