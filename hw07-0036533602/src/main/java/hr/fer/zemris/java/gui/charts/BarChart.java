package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * <code>BarChart</code> contains all information for drawing chart
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public class BarChart {
  /**
   * Contains all points of chart
   */
  private final List<XYValue> xyValueList;

  /**
   * Text below x-axes
   */
  private final String xAxisText;
  /**
   * Text left of y-axes
   */
  private final String yAxisText;

  /**
   * Minimum value on y-axis, can't be lower than one
   */
  private final int minYAxisValue;
  /**
   * Maximum value on y-axis
   */
  private final int maxYAxisValue;

  /**
   * Gap between 2 values on y-axes
   */
  private final int yGap;

  /**
   * Constructor that creates new <code>BarChart</code>
   * @param xyValueList all points of chart
   * @param xAxisText text below x-axes
   * @param yAxisText text left of y-axes
   * @param minYAxisValue minimum value on y-axis, can't be lower than one
   * @param maxYAxisValue maximum value on y-axis
   * @param yGap Gap between 2 values on y-axis
   * @throws IllegalArgumentException if <code>minYAxisValue</code> is less than zero or bigger than <code>maxYAxisValue</code>
   */
  public BarChart(List<XYValue> xyValueList, String xAxisText, String yAxisText, int minYAxisValue, int maxYAxisValue, int yGap) {
    if(minYAxisValue < 0)
      throw new IllegalArgumentException("Min y value can't be negative");

    this.xyValueList = xyValueList;
    this.xAxisText = xAxisText;
    this.yAxisText = yAxisText;
    this.minYAxisValue = minYAxisValue;
    this.maxYAxisValue = maxYAxisValue;

    if((maxYAxisValue - minYAxisValue) % yGap != 0){
      while((maxYAxisValue - minYAxisValue) % yGap!= 0) yGap++;
    }

    this.yGap = yGap;

    for(XYValue xyValue : xyValueList)
      if(xyValue.getY() < minYAxisValue)
        throw new IllegalArgumentException("y value is smaller than min y value");


  }

  /**
   * Fetches all points on chart
   *
   * @return all points on chart
   */
  public List<XYValue> getXyValueList() {
    return xyValueList;
  }

  /**
   * Fetches text below x-axis
   *
   * @return text below x-axis
   */

  public String getxAxisText() {
    return xAxisText;
  }

  /**
   * Fetches text left of y-axis
   *
   * @return text left of y-axis
   */

  public String getyAxisText() {
    return yAxisText;
  }

  /**
   * Fetches minimum y-axis value
   *
   * @return minimum y-axis value
   */
  public int getMinYAxisValue() {
    return minYAxisValue;
  }
  /**
   * Fetches maximum y-axis value
   *
   * @return maximum y-axis value
   */
  public int getMaxYAxisValue() {
    return maxYAxisValue;
  }
  /**
   * Fetches gap between 2 y-axis value
   *
   * @return gap between 2 y-axis value
   */

  public int getyGap() {
    return yGap;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    xyValueList.forEach(xyValueList -> sb.append(xyValueList).append(","));
    return "BarChart{" +
        "xyValueList=" + sb +
        ", xAxisText='" + xAxisText + '\'' +
        ", yAxisText='" + yAxisText + '\'' +
        ", minYAxisValue=" + minYAxisValue +
        ", maxYAxisValue=" + maxYAxisValue +
        ", yGap=" + yGap +
        '}';
  }
}
