package hr.fer.zemris.java.gui.layouts;

import java.awt.*;
import java.util.Map;
import java.util.function.Function;

/**
 * <code>ICalculateDimension</code> is an interface for calculating dimensions for {@link CalcLayout}
 *
 * @author Dean Trkulja
 * @version 1.0
 */

public interface ICalculateDimension {
  /**
   * Calculates dimension for {@link CalcLayout}
   *
   * @param parent parent container
   * @param componentMap map of {@link RCPosition} as key and {@link Component} as value
   * @param getDimensions functional interface
   * @param gap gap between components in <code>parent</code> container
   * @return dimensions for {@link CalcLayout}
   */
  Dimension calculateDimension(Container parent, Map<RCPosition, Component> componentMap, Function<Component,Dimension> getDimensions,int gap);
}
