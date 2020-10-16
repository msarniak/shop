package shop.service;

import java.time.Clock;

/**
 * Access to Clock used by services - to make current date testable.
 */
public class Universe {

  private static Clock clock = Clock.systemUTC();

  public static void useClock(Clock clock) {
    Universe.clock = clock;
  }

  public static Clock getClock() {
    return Universe.clock;
  }

}
