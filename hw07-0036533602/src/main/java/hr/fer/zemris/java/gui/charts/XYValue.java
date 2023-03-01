package hr.fer.zemris.java.gui.charts;

import java.util.Objects;

/**
 *<code>XYValue</code> represents a point characterized by the values on the x-axes and y-axes.
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public class XYValue {
  /**
   * Value on the x-axes
   */
  private final int x;

  /**
   * Value on the y-axes
   */

  private final int y;

  /**
   * Constructor that creates new <code>XYValue</code>
   * @param x value on the x-axes
   * @param y value on the y-axes
   */
  public XYValue(int x, int y){
    this.x = x;
    this.y = y;
  }

  /**
   * Returns value of the x-axes
   *
   * @return value on the x-axes
   */
  public int getX() {
    return x;
  }

  /**
   * Returns value of the y-axes
   *
   * @return value on the y-axes
   */
  public int getY() {
    return y;
  }

  @Override
  public String toString() {
    return String.format("(%d, %d)", this.x, this.y);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    XYValue xyValue = (XYValue) o;
    return x == xyValue.x && y == xyValue.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }
}
