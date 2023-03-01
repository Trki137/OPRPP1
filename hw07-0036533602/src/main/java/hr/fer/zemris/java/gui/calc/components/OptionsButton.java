package hr.fer.zemris.java.gui.calc.components;

import java.awt.event.ActionListener;

/**
 * {@code OptionsButton} class represents a button that when pressed executes one of options operation
 * It extends {@link CalcJButton}
 *
 * @author Dean Trkulja
 * @version 1.0
 */

public class OptionsButton extends CalcJButton{
  /**
   * Creates new <code>OptionsButton</code>
   * @param text text on button
   * @param a action to perform on click
   */
  public OptionsButton(String text, ActionListener a) {
    super(text, a);
  }
}
