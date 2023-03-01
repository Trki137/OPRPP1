package hr.fer.zemris.java.gui.charts;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.Objects;

/**
 * <code>BarChartComponent</code> class that represent component that draws bar chart
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public class BarChartComponent extends JComponent {

  /**
   * Contains all info for drawing bar chart
   */
  private final BarChart barChart;

  /**
   * Color for grid on chart
   */

  private final Color gridYellow = new Color(239,224,196);

  /**
   * Color of main axes
   */

  private final Color axisGray = new Color(157,156,156);

  /**
   * Color of bars
   */
  private final Color rectangleOrange = new Color(244,119,72);

  /**
   * Gap between y-axis and it's values
   */
  private final int gapYValueLine = 7;
  /**
   * Gap between x-axis and it's values
   */
  private final int gapXValueLine = 15;

  /**
   * Gap between text on axes and it's values
   */
  private final int gapBetweenScreenAndValues = 15;

  /**
   * Constructor that creates new <code>BarChartComponent</code>
   * @param barChart contains all info for drawing bar chart
   * @throws NullPointerException if <code>barChart</code> is nuLL
   */
  public BarChartComponent(BarChart barChart){
    Objects.requireNonNull(barChart, "Value can't be null");
    this.barChart = barChart;

  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D graphics2D = (Graphics2D) g;
    Insets insets = getInsets();
    FontMetrics fontMetrics = graphics2D.getFontMetrics();
    Dimension size = getSize();


    int maxWidthYValue = getMaxWidthYValue(graphics2D);
    int xStart = getX() + insets.left + maxWidthYValue + gapYValueLine + gapBetweenScreenAndValues + 20;
    int yStart = getY() + insets.top;
    int heightOfXAxisTitle = fontMetrics.getHeight();
    int chartWidth = size.width - xStart - insets.right;
    int chartHeight = size.height  - heightOfXAxisTitle - 20;

    int numOfXValues = barChart.getXyValueList().size();
    int widthBetweenTwoValues = (chartWidth-xStart-5) / numOfXValues;

    drawCoordinateGrid(graphics2D,insets,size, xStart,yStart,chartWidth,chartHeight,widthBetweenTwoValues);
    drawRectangles(graphics2D,insets,size,xStart,yStart,chartWidth,chartHeight,widthBetweenTwoValues);

  }

  /**
   * Method that draws bars on chart
   *
   * @param graphics2D {@link Graphics2D} instance used for drawing on the component.
   * @param insets of parent container
   * @param size dimension of parent container
   * @param xStart where x-axes starts
   * @param yStart where y-axes starts
   * @param chartWidth width of chart
   * @param chartHeight height of chart
   * @param widthBetweenTwoValues width between 2 values on x-axes
   */
  private void drawRectangles(Graphics2D graphics2D, Insets insets, Dimension size, int xStart, int yStart, int chartWidth, int chartHeight,int widthBetweenTwoValues) {
    List<XYValue> list = barChart.getXyValueList();

    int gapBetweenRows = barChart.getyGap();
    int end = barChart.getMaxYAxisValue();
    graphics2D.setColor(rectangleOrange);

    int rowSize = (chartHeight - yStart - 2) / (end / gapBetweenRows);


    for (int i = 0; i < list.size(); i++) {
      int yValue = list.get(i).getY();
      double numOfSquares = yValue * 1.0 / gapBetweenRows;
      int rectangleHeight = (int)Math.round(numOfSquares * rowSize);
      graphics2D.fillPolygon(new Polygon(
          new int[]{xStart + i * widthBetweenTwoValues, -1 + xStart + (i+1)*widthBetweenTwoValues , -1 + xStart + (i+1)*widthBetweenTwoValues,xStart + i*widthBetweenTwoValues},
          new int[]{chartHeight - rectangleHeight,chartHeight - rectangleHeight,chartHeight,chartHeight},
          4
      ));

    }

    
  }

  /**
   * Method that draws grid and main axes
   *
   * @param graphics2D {@link Graphics2D} instance used for drawing on the component.
   * @param insets of parent container
   * @param size dimension of parent container
   * @param xStart where x-axes starts
   * @param yStart where y-axes starts
   * @param chartWidth width of chart
   * @param chartHeight height of chart
   * @param widthBetweenTwoValues width between 2 values on x-axes
   */

  private void drawCoordinateGrid(Graphics2D graphics2D, Insets insets, Dimension size, int xStart, int yStart, int chartWidth,int chartHeight,int widthBetweenTwoValues) {
    Objects.requireNonNull(graphics2D);
    Objects.requireNonNull(insets);
    Objects.requireNonNull(size);

    FontMetrics fontMetrics = graphics2D.getFontMetrics();
    graphics2D.setFont(new Font(null, Font.BOLD,10));

    graphics2D.setColor(axisGray);
    graphics2D.setStroke(new BasicStroke(2));

    //Main x axis
    graphics2D.drawLine(xStart, chartHeight, chartWidth, chartHeight);
    //Main y axis
    graphics2D.drawLine(xStart, yStart, xStart, chartHeight);


    graphics2D.drawLine(xStart, chartHeight, xStart, chartHeight + 5);

    List<XYValue> list = barChart.getXyValueList();
    int prevXAxis = xStart;
    //for loop for y axis values
    for(int i = 0; i < list.size(); i++){
      int xAxis = xStart + widthBetweenTwoValues * (i + 1);
      graphics2D.setColor(gridYellow);
      graphics2D.drawLine(xAxis,yStart, xAxis,chartHeight);

      graphics2D.setColor(axisGray);
      graphics2D.drawLine(xAxis, chartHeight,  xAxis, chartHeight + 5);

      graphics2D.setColor(Color.BLACK);
      graphics2D.drawString(String.valueOf(list.get(i).getX()), (xAxis + prevXAxis) / 2, chartHeight + gapXValueLine);

      prevXAxis = xAxis;
    }

    //while loop for x-axis values
    int gapBetweenRows = barChart.getyGap();
    int start = barChart.getMinYAxisValue();
    int end = barChart.getMaxYAxisValue();
    int counter = 0;
    int rowSize = (chartHeight - yStart - 2) / (end / gapBetweenRows);

    while(start <= end){
      int yAxis = chartHeight - counter * rowSize;
      int widthYValue = fontMetrics.stringWidth(String.valueOf(start));
      if(counter != 0) {
        graphics2D.setColor(gridYellow);
        graphics2D.drawLine(xStart, yAxis, chartWidth, yAxis);
      }
      graphics2D.setColor(axisGray);
      graphics2D.drawLine(xStart -5, yAxis, xStart,yAxis);

      graphics2D.setColor(Color.BLACK);
      graphics2D.drawString(String.valueOf(start), xStart - gapYValueLine - widthYValue, yAxis + (graphics2D.getFontMetrics().getHeight() / 4));

      start += gapBetweenRows;
      counter++;
    }

    graphics2D.setColor(axisGray);
    graphics2D.fillPolygon(new Polygon(new int[] {chartWidth, chartWidth, chartWidth + 7}, new int[]{chartHeight + 3, chartHeight - 3, chartHeight}, 3));
    graphics2D.fillPolygon(new Polygon(new int[] {xStart - 3, xStart  + 3, xStart}, new int[]{yStart, yStart, yStart - 7}, 3));

    //x-axis title
    graphics2D.setFont(new Font(null, Font.PLAIN,10));
    graphics2D.setColor(Color.BLACK);
    int xAxisTextXCoordinate = size.width / 2 - fontMetrics.stringWidth(barChart.getxAxisText()) / 2;
    graphics2D.drawString(barChart.getxAxisText(), xAxisTextXCoordinate , size.height -5);

    //y-axis title
    Font oldFont = graphics2D.getFont();
    Font newFont = oldFont.deriveFont(AffineTransform.getRotateInstance(-Math.PI / 2));
    graphics2D.setFont(newFont);

    int yAxisTextYCoordinate = size.height / 2 + fontMetrics.stringWidth(barChart.getyAxisText()) / 4;
    graphics2D.drawString(barChart.getyAxisText(), gapBetweenScreenAndValues, yAxisTextYCoordinate );

    graphics2D.setFont(oldFont);


  }

  /**
   * Gets max width of all values on y-axes
   *
   * @param graphics2D  {@link Graphics2D} instance used for drawing on the component.
   * @return maximum width of values on y-axes
   */
  private int getMaxWidthYValue(Graphics2D graphics2D) {
    Objects.requireNonNull(graphics2D);

    int start = barChart.getMinYAxisValue();
    int end = barChart.getMinYAxisValue();
    int max = 0;

    while(start <= end){
      int width = graphics2D.getFontMetrics().stringWidth(String.valueOf(start));
      if(width > max) max = width;
      start += barChart.getyGap();
    }

    return max;
  }
}
