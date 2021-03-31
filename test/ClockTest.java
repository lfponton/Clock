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

  // Test methods for three parameters set()
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
    assertEquals("23:0:0",valueOf(clock));

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

  // Test methods for one parameter set()

  @Test void set1ArgsZero()
  {
    clock.set(0);
    assertEquals("0:0:0", valueOf(clock));
  }

  @Test void set1ArgsOne()
  {
    clock.set(1);
    assertEquals("0:0:1", valueOf(clock));
  }

  @Test void set1ArgsMany()
  {
    clock.set(185);
    assertEquals("0:3:5", valueOf(clock));
    clock.set(86000);
    assertEquals("23:53:20", valueOf(clock));
    clock.set(86000 + 86400); // tomorrow
    assertEquals("23:53:20", valueOf(clock));
    clock.set(86000 + 7 * 86400); // one week later
    assertEquals("23:53:20", valueOf(clock));
  }

  @Test void set1ArgsBoundary()
  {
    // right lower bound: 0 [already done in set1ArgsZero()]

    // left lower bound: -1
    assertThrows(IllegalArgumentException.class, () -> clock.set(-1));

    // left upper bound: 86399
    clock.set(86399);
    assertEquals("23:59:59", valueOf(clock));

    // right upper bound: 86400
    clock.set(86400);
    assertEquals("0:0:0", valueOf(clock));
  }

  @Test void set1ArgsException()
  {
    assertThrows(IllegalArgumentException.class, () -> clock.set(-26));
    assertThrows(IllegalArgumentException.class, () -> clock.set(-90000));
  }

  @Test void getTimeInSecondsZero()
  {
    assertEquals(0, clock.getTimeInSeconds());
  }

  @Test void getTimeInSecondsOne()
  {
    clock.set(0,0,1);
    assertEquals(1,clock.getTimeInSeconds());
  }

  @Test void getTimeInSecondsMany()
  {
    clock.set(0, 3, 5);
    assertEquals(185, clock.getTimeInSeconds());
    clock.set(23, 53, 20);
    assertEquals(86000, clock.getTimeInSeconds());
  }

  @Test void getTimeInSecondsBoundary()
  {
    // lower left: -1 [not possible]
    // lower right: 0 [already done in getTimeInSecondsZero()]
    // upper right: 0 [already done in getTimeInSecondsZero()]

    // upper left
    clock.set(23, 59, 59);
    assertEquals(86399, clock.getTimeInSeconds());
  }

  @Test void getTimeInSecondsException()
  {
    // No exceptions can be thrown
  }

  @Test void ticZero()
  {
    // No need to check zero
  }

  @Test void ticOne()
  {
    clock.tic();
    assertEquals("0:0:1", valueOf(clock));
  }

  @Test void ticMany()
  {
    for (int i = 0; i < 185; i++)
    {
      clock.tic();
    }
    assertEquals("0:3:5", valueOf(clock));

    clock.set(0,0,0);
    for (int i = 0; i < 86000; i++)
    {
      clock.tic();
    }
    assertEquals("23:53:20", valueOf(clock));

    clock.set(0,0,0);
    for (int i = 0; i < 86000 + 86400 * 7; i++)
    {
      clock.tic();
    }
    assertEquals("23:53:20", valueOf(clock));
  }

  @Test void ticBoundary()
  {
    // left and right for second
    for (int i = 0; i < 59; i++)
    {
      clock.tic();
    }
    assertEquals("0:0:59", valueOf(clock));
    clock.tic();
    assertEquals("0:1:0", valueOf(clock));

    // left and right for minute
    clock.set(0,0,0);
    for (int i = 0; i < 3599; i++)
    {
      clock.tic();
    }
    assertEquals("0:59:59", valueOf(clock));
    clock.tic();
    assertEquals("1:0:0", valueOf(clock));

    // left and right for hour
    clock.set(0,0,0);
    for (int i = 0; i < 86399; i++)
    {
      clock.tic();
    }
    assertEquals("23:59:59", valueOf(clock));
    clock.tic();
    assertEquals("0:0:0", valueOf(clock));
  }

  @Test void toStringZero()
  {
    assertEquals("00:00:00", clock.toString());
  }

  @Test void toStringOne()
  {
    clock.set(0, 0,1);
    assertEquals("00:00:01", clock.toString());
    clock.set(0, 1,0);
    assertEquals("00:01:00", clock.toString());
    clock.set(1, 0,0);
    assertEquals("01:00:00", clock.toString());
  }

  @Test void toStringMany()
  {
    clock.set(0,5,3);
    assertEquals("00:05:03", clock.toString());
    clock.set(12,52,7);
    assertEquals("12:52:07", clock.toString());
  }

  @Test void toStringBoundary()
  {
    // lower right: 0 [tested in toStringZero()]
    // upper right: 0 [toStringOne(), toStringZero()]
    // lower left: not possible

    // upper left: 59 for seconds and minutes and 23 for hours
    clock.set(0,0,59);
    assertEquals("00:00:59", clock.toString());
    clock.set(0,59,0);
    assertEquals("00:59:00", clock.toString());
    clock.set(23,0,0);
    assertEquals("23:00:00", clock.toString());
    clock.set(23,59,59);
    assertEquals("23:59:59", clock.toString());

    // boundary for two digits, left: 9
    clock.set(0,0,9);
    assertEquals("00:00:09", clock.toString());
    clock.set(0,9,0);
    assertEquals("00:09:00", clock.toString());
    clock.set(9,0,0);
    assertEquals("09:00:00", clock.toString());

    // boundary for two digits, right: 10
    clock.set(0,0,10);
    assertEquals("00:00:10", clock.toString());
    clock.set(0,10,0);
    assertEquals("00:10:00", clock.toString());
    clock.set(10,0,0);
    assertEquals("10:00:00", clock.toString());
  }

  @Test void toStringException()
  {
    // not possible
  }
}