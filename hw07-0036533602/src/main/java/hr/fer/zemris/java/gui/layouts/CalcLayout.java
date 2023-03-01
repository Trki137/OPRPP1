package hr.fer.zemris.java.gui.layouts;

import java.awt.*;
import java.util.*;
import java.util.function.Function;

/**
 * <code>CalcLayout</code> is class that implements {@link LayoutManager2}.
 * <code>CalcLayout</code> is class that models layout for {@link hr.fer.zemris.java.gui.calc.Calculator}
 *
 * @author Dean Trkulja
 * @version 1.0
 */

public class CalcLayout implements LayoutManager2 {

  /**
   * Maximum number of rows
   */
  private static final int NUM_OF_ROWS = 5;

  /**
   * Maximum number of columns
   */
  private static final int NUM_OF_COLUMNS = 7;

  /**
   * Maximum number of elements in layout
   */
  private final int maxSize = 31;

  /**
   * Map for storing components
   */
  private final Map<RCPosition, Component> componentMap;

  /**
   * Gap between rows and columns that are next to each other
   */
  private final int gap;

  /**
   * Default constructor that sets the gap to 0
   */
  public CalcLayout() {
    this(0);
  }

  /**
   * Constructor that creates new <code>CalcLayout</code>
   * @param gap Gap between rows and columns that are next to each other
   */
  public CalcLayout(int gap) {
    this.gap = gap;
    this.componentMap = new TreeMap<>(Comparator.comparingInt(RCPosition::getRow).thenComparingInt(RCPosition::getColumn));
  }


  @Override
  public void addLayoutComponent(Component comp, Object constraints) {
    Objects.requireNonNull(comp, "Component can't be null");
    Objects.requireNonNull(constraints, "Constraint can't be null");

    if (!(constraints instanceof String || constraints instanceof RCPosition))
      throw new IllegalArgumentException();

    RCPosition position = null;

    if (constraints instanceof String)
      position = RCPosition.parse((String) constraints);

    if (constraints instanceof RCPosition)
      position = (RCPosition) constraints;


    validateConstraints(position);

    componentMap.put(position, comp);
  }


  @Override
  public void removeLayoutComponent(Component comp) {
    Objects.requireNonNull(comp);

    if (!componentMap.containsValue(comp)) {
      throw new IllegalArgumentException("Component doesn't exists");
    }

    RCPosition position = null;

    for (var keyValueSet : componentMap.entrySet()) {
      if (keyValueSet.getValue().equals(comp)) {
        position = keyValueSet.getKey();
        break;
      }
    }

    componentMap.remove(position);

  }


  @Override
  public float getLayoutAlignmentX(Container target) {
    return 0;
  }

  @Override
  public float getLayoutAlignmentY(Container target) {
    return 0;
  }

  @Override
  public void invalidateLayout(Container target) {

  }

  @Override
  public void addLayoutComponent(String name, Component comp) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Dimension preferredLayoutSize(Container parent) {
    CalculateDimensions calculateDimensions = new CalculateDimensions();
    return calculateDimensions.calculateDimension(parent, componentMap, Component::getPreferredSize, gap);
  }

  @Override
  public Dimension minimumLayoutSize(Container parent) {
    CalculateDimensions calculateDimensions = new CalculateDimensions();
    return calculateDimensions.calculateDimension(parent, componentMap, Component::getMinimumSize, gap);
  }

  @Override
  public Dimension maximumLayoutSize(Container target) {
    CalculateDimensions calculateDimensions = new CalculateDimensions();
    return calculateDimensions.calculateDimension(target, componentMap, Component::getMaximumSize, gap);
  }

  @Override
  public void layoutContainer(Container parent) {
    Objects.requireNonNull(parent);
    Insets ins = parent.getInsets();

    int[] widthOfComponents = calculate(parent.getWidth(), ins.right + ins.left, NUM_OF_COLUMNS);
    int[] heightOfComponents = calculate(parent.getHeight(), ins.top + ins.bottom, NUM_OF_ROWS);

    int xOffset;
    int yOffset;


    for (var keyValueSet : componentMap.entrySet()) {
      int xPosition = keyValueSet.getKey().getRow();
      int yPosition = keyValueSet.getKey().getColumn();

      xOffset = gap * (yPosition - 1);
      yOffset = gap * (xPosition - 1);

      Component c = keyValueSet.getValue();

      if (keyValueSet.getKey().equals(new RCPosition(1, 1))) {

        int width = 4 * gap ;
        for (int i = 1; i <= 5; i++) width += widthOfComponents[i];

        c.setBounds(xOffset, yOffset, width, heightOfComponents[xPosition]);

        continue;
      }

      for(int i = 1; i < yPosition; i++) xOffset += widthOfComponents[i];
      for(int j = 1; j < xPosition; j++) yOffset += heightOfComponents[j];

      Rectangle r = new Rectangle(xOffset, yOffset, widthOfComponents[yPosition],heightOfComponents[xPosition]);

      c.setBounds(r);


    }

  }

  /**
   * Calculates the uniform distribution for our components
   *
   * @param size width or height of a container
   * @param inset inset of parent container
   * @param numOfElements number of elements in a single column or row
   * @return array of integers that are the sizes of components
   */

  private int[] calculate(int size, int inset, int numOfElements) {
    int[] res = new int[numOfElements + 1];
    int length = size - inset - gap * (numOfElements - 1);

    if (length % numOfElements == 0) {
      Arrays.fill(res, length / numOfElements);
      return res;
    }

    for (int i = 1; i <= numOfElements; i++) {
      res[i] = (int) Math.round(length * i * 1.0 / numOfElements);
      for (int j = i - 1; j >= 1; j--) res[i] -= res[j];
    }
    return res;
  }

  /**
   * Checks if <code>position</code> is valid position in our layout
   *
   * @param position position in grid
   * @throws CalcLayoutException if <code>position</code> is invalid
   */
  private void validateConstraints(RCPosition position) {
    Objects.requireNonNull(position);

    int row = position.getRow();
    int column = position.getColumn();

    if (componentMap.size() == maxSize)
      throw new CalcLayoutException("All spaces are full");

    if (row < 1 || row > NUM_OF_ROWS)
      throw new CalcLayoutException("Invalid row. Expected row value between 1 and " + NUM_OF_ROWS + ", but got " + row);

    if (column < 1 || column > NUM_OF_COLUMNS)
      throw new CalcLayoutException("Invalid column. Expected column value between 1 and " + NUM_OF_COLUMNS + ", but got " + column);

    if (row == 1) {
      if (column > 1 && column < 6)
        throw new CalcLayoutException("Invalid position " + position);
    }

    if (componentMap.containsKey(position))
      throw new CalcLayoutException("Component already exists on position " + position);

  }


  public int getNumOfRows() {
    return NUM_OF_ROWS;
  }

  public int getNumOfColumns() {
    return NUM_OF_COLUMNS;
  }

  public int getMaxSize() {
    return maxSize;
  }

  /**
   * <code>CalculateDimensions</code> implements {@link ICalculateDimension}
   * <code>CalculateDimensions</code> class models how to calculate dimensions for <code>CalcLayout</code>
   *
   * @author Dean Trkulja
   * @version 1.0
   */
  private static class CalculateDimensions implements ICalculateDimension{

    @Override
    public Dimension calculateDimension(Container parent,Map<RCPosition, Component> componentMap, Function<Component, Dimension> getDimensions,int gap) {
      int maxWidth = 0;
      int maxHeight = 0;

      Dimension d;

      for(var keyValue : componentMap.entrySet()){
        RCPosition position = keyValue.getKey();
        Component c = keyValue.getValue();

        d = getDimensions.apply(c);

        int width = d.width;
        int height = d.height;

        if(position.equals(new RCPosition(1,1))){
          width -= gap * 4;
          width /= 5;
        }

        if(width > maxWidth) maxWidth = width;
        if(height > maxHeight) maxHeight = height;

      }

      Insets ins = parent.getInsets();

      int prefWidth = maxWidth * NUM_OF_COLUMNS + gap * (NUM_OF_COLUMNS - 1) - (ins.left + ins.right);
      int prefHeight = maxHeight * NUM_OF_ROWS + gap * (NUM_OF_ROWS - 1) - (ins.bottom + ins.top);

      return new Dimension(prefWidth,prefHeight);
    }
  }

}
