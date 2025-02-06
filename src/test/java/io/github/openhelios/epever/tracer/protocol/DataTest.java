package io.github.openhelios.epever.tracer.protocol;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DataTest {

  @Test
  void testUint16() {
    final byte[] data = new byte[] { 0xB, (byte) 0xB8 };
    assertEquals(3000, Data.uint16(data, 0));
  }

}
