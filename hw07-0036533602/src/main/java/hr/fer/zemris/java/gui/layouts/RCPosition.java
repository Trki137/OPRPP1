package hr.fer.zemris.java.gui.layouts;


import java.util.Objects;
import java.util.regex.Pattern;

/**
 * <code>RCPosition</code> models the position of a grid element with a defined row and column.
 *
 * @author Dean Trkulja
 * @version 1.0
 */

public class RCPosition {
  /**
   * Row in grid
   */
  private final int row;

  /**
   * Column in grid
   */
  private final int column;

  /**
   * Constructor that creates new <code>RCPosition</code>
   * @param row row of the grid
   * @param column column of the grid
   */
  public RCPosition(int row, int column){
    this.row = row;
    this.column = column;
  }


  /**
   * Parses <code>text</code> and return new <code>RCPosition</code>
   *
   * @param text position in grid
   * @return new <code>RCPosition</code> that is parsed from <code>text</code>
   * @throws NullPointerException if <code>text</code> is null
   * @throws IllegalArgumentException if <code>text</code> is not in format row,column
   */
  public static RCPosition parse(String text){
    Objects.requireNonNull(text, "Value can't be null");

    Pattern regex = Pattern.compile("\\d+,\\d+");

    if(!regex.matcher(text).matches())
      throw new IllegalArgumentException("Invalid format");

    String[] coordinates = text.split(",");

    int row = Integer.parseInt(coordinates[0]);
    int column = Integer.parseInt(coordinates[1]);

    return new RCPosition(row,column);
  }

  /**
   *
   * @return index of column in grid
   */

  public int getColumn() {
    return column;
  }

  /**
   *
   * @return index of row in grid
   */

  public int getRow() {
    return row;
  }

  @Override
  public String toString() {
    return String.format("(%d,%d)", row, column);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RCPosition that = (RCPosition) o;
    return row == that.row && column == that.column;
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, column);
  }
}
