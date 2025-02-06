package io.github.openhelios.epever.tracer.protocol.response;

import java.time.LocalDateTime;

import io.github.openhelios.epever.tracer.protocol.Data;
import io.github.openhelios.epever.tracer.protocol.Index;

/**
 * The response with battery type data.
 *
 * @param second The seconds.
 * @param minute The minute.
 * @param hour The hour.
 * @param day The day.
 * @param month The month.
 * @param year The year.
 */
public record Clock( //
    int second, //
    int minute, //
    int hour, //
    int day, //
    int month, //
    int year //
) implements Response {

  /** The time in nanoseconds needed to update the clock on the device, which is approximately 1.5 seconds. */
  public static long UPDATE_TIME_IN_NANO_SECONDS = 1500000000; // 1.5 seconds

  /**
   * Constructor.
   *
   * @param secondsMinutes The seconds and minutes.
   * @param hourDay The hour and day.
   * @param monthYear The month and Year.
   */
  private Clock(final int secondsMinutes, final int hourDay, final int monthYear) {
    this( //
        Data.bits(secondsMinutes, 0, 7), //
        Data.bits(secondsMinutes, 8, 15), //
        Data.bits(hourDay, 0, 7), //
        Data.bits(hourDay, 8, 15), //
        Data.bits(monthYear, 0, 7), //
        Data.bits(monthYear, 8, 15) //
    );
  }

  /**
   * Constructor.
   *
   * @param message The message.
   */
  public Clock(final byte[] message) {
    this( //
        Data.uint16(message, Index.DATA.get()), //
        Data.uint16(message, Index.DATA.get() + 2), //
        Data.uint16(message, Index.DATA.get() + 4) //
    );
  }

  /**
   * Constructor.
   *
   * @param dateTime The date time.
   */
  public Clock(final LocalDateTime dateTime) {
    this(dateTime.getSecond(), dateTime.getMinute(), dateTime.getHour(), dateTime.getDayOfMonth(),
        dateTime.getMonthValue(), dateTime.getYear() - 2000);
  }

  /**
   * Constructor with the current time plus {@link #UPDATE_TIME_IN_NANO_SECONDS}.
   */
  public Clock() {
    this(LocalDateTime.now().plusNanos(UPDATE_TIME_IN_NANO_SECONDS));
  }

  /**
   * Creates the bytes for this clock.
   *
   * @return The bytes for this clock.
   */
  public byte[] toData() {
    return new byte[] { (byte) minute, (byte) second, (byte) day, (byte) hour, (byte) year, (byte) month };
  }

  @Override
  public final String toString() {
    return "Clock " + String.format("20%02d-%02d-%02d %02d:%02d:%02d", year, month, day, hour, minute, second);
  }

}
