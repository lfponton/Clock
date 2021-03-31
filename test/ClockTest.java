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
    clock.set(12,15,20);
    assertEquals("12:15:20", valueOf(clock));
    clock.set(7,41,9);
    assertEquals("7:41:9", valueOf(clock));
  }

  @Test void setBoundary()
  {
    // lower left boundary: -1
    assertThrows(IllegalArgumentException.class, () -> clock.set(0,0,-1));
    assertThrows(IllegalArgumentException.class, () -> clock.set(0,-1,0));
    assertThrows(IllegalArgumentException.class, () -> clock.set(-1,0,0));
    // lower right boundary: 0 [already tested in setZero()]

    // upper left boundary: 59 for second and minute, 23 for hour
    clock.set(0,0,59);
    assertEquals("0:0:59",valueOf(clock));
    clock.set(0,59,0);
    assertEquals("0:59:0",valueOf(clock));
    clock.set(23,0,0);
    assertEquals("59:0:0",valueOf(clock));

    // upper right boundary: 60 for second and minute, 24 for hour
    assertThrows(IllegalArgumentException.class, () -> clock.set(0,0,60));
    assertThrows(IllegalArgumentException.class, () -> clock.set(0,60,0));
    assertThrows(IllegalArgumentException.class, () -> clock.set(24,0,0));
  }

  @Test void setException()
  {
    // negative values
    assertThrows(IllegalArgumentException.class, () -> clock.set(0, 0, -5));
    assertThrows(IllegalArgumentException.class, () -> clock.set(0, -5, 0));
    assertThrows(IllegalArgumentException.class, () -> clock.set(-5, 0, 0));

    // values > 23 for hour and > 59 for seconds and minutes
    assertThrows(IllegalArgumentException.class, () -> clock.set(0,0,71));
    assertThrows(IllegalArgumentException.class, () -> clock.set(0,71,0));
    assertThrows(IllegalArgumentException.class, () -> clock.set(41,0,0));
  }
}