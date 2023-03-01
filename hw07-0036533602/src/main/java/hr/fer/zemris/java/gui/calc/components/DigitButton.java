package hr.fer.zemris.java.gui.calc.components;

import java.awt.event.ActionListener;


/**
 * {@code OptionsButton} class represents a number button that when pressed adds digit to calculator screen
 * It extends {@link CalcJButton}
 *
 * @author Dean Trkulja
 * @version 1.0
 */

public class DigitButton extends CalcJButton{


  /**
   * Creates new <code>OptionsButton</code>
   * @param text text on button
   * @param a action to perform on click
   */
  public DigitButton(String text, ActionListener a) {
    super(text,a);
  }

  @Override
  protected void initGui(){
    super.initGui();
    setFont(getFont().deriveFont(30f));
  }
}
