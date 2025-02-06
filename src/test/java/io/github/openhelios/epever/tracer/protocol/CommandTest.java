package io.github.openhelios.epever.tracer.protocol;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CommandTest {

  @Test
  void testBatteryChargingDischargingStatus() {
    Assertions.assertArrayEquals(ChecksumTest.REQUEST_STATUS, Command.batteryChargingDischargingStatus().get());
  }

}
