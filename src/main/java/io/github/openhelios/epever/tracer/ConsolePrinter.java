package io.github.openhelios.epever.tracer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import io.github.openhelios.epever.tracer.protocol.response.Response;

/**
 * A console printer of the values received from DPS-150.
 */
public class ConsolePrinter implements Tracer3210ANListener {

  /** The date time formatter to prefix each line. */
  public final static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS");

  /**
   * Prints the given message with a date time stamp as prefix.
   *
   * @param message The message.
   */
  public static void println(final String message) {
    System.out.println(DTF.format(LocalDateTime.now()) + " " + message);
  }

  /**
   * The default constructor.
   */
  public ConsolePrinter() {
    // do nothing
  }

  @Override
  public void onMessage(final Response response) {
    println(response.toString());
  }

}
