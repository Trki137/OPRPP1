package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class RCPositionTest {

  @Test
  public void testParse(){
    RCPosition position1 = RCPosition.parse("1,23");
    RCPosition position2 = RCPosition.parse("11,22");
    RCPosition position3 = RCPosition.parse("10,9");

    assertEquals(1, position1.getRow());
    assertEquals(11, position2.getRow());
    assertEquals(10, position3.getRow());
    assertEquals(23, position1.getColumn());
    assertEquals(22, position2.getColumn());
    assertEquals(9, position3.getColumn());
  }

  @Test
  public void testParseThrows(){
    assertThrows(IllegalArgumentException.class, () -> RCPosition.parse("a,2"));
    assertThrows(NullPointerException.class, () -> RCPosition.parse(null));
  }
}
