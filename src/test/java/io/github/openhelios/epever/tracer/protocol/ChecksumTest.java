package io.github.openhelios.epever.tracer.protocol;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for methods in {@link Checksum}.
 */
class ChecksumTest {

  /** Request for battery, charging and discharging status. */
  public static final byte[] REQUEST_STATUS = new byte[] { 0x01, 0x04, 0x32, 0x00, 0x00, 0x03, (byte) 0xBE, (byte) 0xB3 };

  @Test
  void testCheck() {
    assertNull(Checksum.check(REQUEST_STATUS));
  }

  @Test
  void testCheckWithError() {
    final byte[] data = new byte[] { 0x01, 0x04, 0x32, 0x00, 0x00, 0x03, 0x01, 0x02 };
    Assertions.assertEquals("expected checksum BE B3 , but was 01 02  in 01 04 32 00 00 03 01 02 ",
        Checksum.check(data));
  }

}
