import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClockTest
{
  private Clock clock;
  @BeforeEach void setup()
  {
    clock = new Clock(0,0,0);
  }

  @AfterEach void tearDown()
  {

  }

  private String valueOf(Clock clock)
  {
    return clock.getHour() + ":" + clock.getMinute() + ":" + clock.getSecond();
  }

  @Test void setZero()
  {
    clock.set(0,0,0);
    assertEquals("0:0:0", valueOf(clock));
  }

  @Test void setOne()
  {
    clock.set(0,0,1);
    assertEquals("0:0:1", valueOf(clock));
    clock.set(0,1,0);
    assertEquals("0:1:0", valueOf(clock));
    clock.set(1,0,0);
    assertEquals("1:0:0", valueOf(clock));
  }

  @Test void setMany()
  {
    System.out.println("  - setMany()");
  }

  @Test void setBoundary()
  {
    System.out.println("  - setBoundary()");
  }

  @Test void setException()
  {
    System.out.println("  - setException()");
  }
}