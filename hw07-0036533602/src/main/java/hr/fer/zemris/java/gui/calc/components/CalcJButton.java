package hr.fer.zemris.java.gui.calc.components;


import hr.fer.zemris.java.gui.calc.Calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * <code>CalcJButton</code> class represents {@link JButton} with some modifications for our {@link Calculator}
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public abstract class CalcJButton extends JButton {
  /**
   * Text on button
   */
  protected final String text;

  /**
   * Creates new <code>CalcJButton</code>
   *
   * @param text text on button
   * @param a action on click
   */
  public CalcJButton(String text, ActionListener a){
    addActionListener(a);
    this.text = text;
    initGui();
  }

  /**
   * Styles component
   */
  protected void initGui(){
    setText(text);
    setBackground(new Color(221,221,255));
    setBorder(BorderFactory.createLineBorder(Color.BLACK));
    setHorizontalAlignment(SwingConstants.CENTER);
    setVerticalAlignment(SwingConstants.CENTER);

  }

  public void updateText(){}

}
