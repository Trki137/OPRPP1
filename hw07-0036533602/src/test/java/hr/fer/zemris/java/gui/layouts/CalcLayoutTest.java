package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

public class CalcLayoutTest {

  @Test
  public void testRowGreaterThanMaxThrows(){
    int maxRows = new CalcLayout().getNumOfRows();
    assertThrows(CalcLayoutException.class,() -> new CalcLayout().addLayoutComponent(new JButton(),new RCPosition(maxRows + 1, 1)));
  }

  @Test
  public void testRowSmallerThanOneThrows(){
    assertThrows(CalcLayoutException.class,() -> new CalcLayout().addLayoutComponent(new JButton(),new RCPosition(0, 1)));
  }

  @Test
  public void testColumnGreaterThanMaxThrows(){
    int maxColums = new CalcLayout().getNumOfColumns();
    assertThrows(CalcLayoutException.class,() -> new CalcLayout().addLayoutComponent(new JButton(),new RCPosition(1, maxColums + 1)));
  }

  @Test
  public void testColumnSmallerThanOneThrows(){
    assertThrows(CalcLayoutException.class,() -> new CalcLayout().addLayoutComponent(new JButton(),new RCPosition(1, 0)));
  }

  @Test
  public void testFirstRowThrows(){
    assertThrows(CalcLayoutException.class, () -> new CalcLayout().addLayoutComponent(new JButton(), new RCPosition(1,2)));
    assertThrows(CalcLayoutException.class, () -> new CalcLayout().addLayoutComponent(new JButton(), new RCPosition(1,3)));
    assertThrows(CalcLayoutException.class, () -> new CalcLayout().addLayoutComponent(new JButton(), new RCPosition(1,4)));
    assertThrows(CalcLayoutException.class, () -> new CalcLayout().addLayoutComponent(new JButton(), new RCPosition(1,5)));
  }

  @Test
  public void testAddMultipleToSamePositionThrows(){
    CalcLayout calcLayout = new CalcLayout();
    RCPosition position = new RCPosition(1,1);
    calcLayout.addLayoutComponent(new JButton(), position);
    assertThrows(CalcLayoutException.class, () -> calcLayout.addLayoutComponent(new JButton(), position));
  }

  @Test
  public void testOverMaxSizeThrows(){
    CalcLayout calcLayout = new CalcLayout();

    int rows = calcLayout.getNumOfRows();
    int columns = calcLayout.getNumOfColumns();

    calcLayout.addLayoutComponent(new JButton(), new RCPosition(1,1));
    calcLayout.addLayoutComponent(new JButton(), new RCPosition(1,6));
    calcLayout.addLayoutComponent(new JButton(), new RCPosition(1,7));

    for(int i = 2; i <= rows; i++){
      for(int j = 1; j <= columns; j++)
        calcLayout.addLayoutComponent(new JButton(), new RCPosition(i,j));
    }

    assertThrows(CalcLayoutException.class,() -> calcLayout.addLayoutComponent(new JButton(), new RCPosition(1,1)));
  }

  @Test
  public void testNullCompAndNullRCPositionThrows(){
    assertThrows(NullPointerException.class, () -> new CalcLayout().addLayoutComponent(null, new RCPosition(1,1)));
    assertThrows(NullPointerException.class, () -> new CalcLayout().addLayoutComponent(new JButton(), null));
  }

  @Test
  public void testNotRCPositionThrows(){
    assertThrows(IllegalArgumentException.class, () -> new CalcLayout().addLayoutComponent(new JButton(), "d"));
  }

  @Test
  public void testDimensions1(){
    JPanel p = new JPanel(new CalcLayout(2));
    JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
    JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));
    p.add(l1, new RCPosition(2,2));
    p.add(l2, new RCPosition(3,3));
    Dimension dim = p.getPreferredSize();
    Dimension expectedSize = new Dimension(152,158);

    assertEquals(expectedSize,dim);
  }

  @Test
  public void testDimensions2(){
    JPanel p = new JPanel(new CalcLayout(2));
    JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
    JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
    p.add(l1, new RCPosition(1,1));
    p.add(l2, new RCPosition(3,3));
    Dimension dim = p.getPreferredSize();
    Dimension expectedSize = new Dimension(152,158);

    assertEquals(expectedSize,dim);
  }


}
