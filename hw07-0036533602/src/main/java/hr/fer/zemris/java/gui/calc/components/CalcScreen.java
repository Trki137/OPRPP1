package hr.fer.zemris.java.gui.calc.components;

import hr.fer.zemris.java.gui.calc.Calculator;

import javax.swing.*;
import java.awt.*;

/**
 * <code>CalcScreen</code> class represent {@link Calculator} screen.
 * <code>CalcScreen</code> extends {@link JLabel}
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public class CalcScreen extends JLabel{
  /**
   * Text on screen
   */
  private String text;

  /**
   * Creates new <code>CalcScreen</code>
   *
   * @param text initial text on screen
   */
  public CalcScreen(String text){
    this.text = text;
    initGui();
  }

  /**
   * Styles component
   */
  protected void initGui(){
    setText(text);
    setBackground(Color.YELLOW);
    setFont(getFont().deriveFont(30f));
    setOpaque(true);
    setHorizontalAlignment(SwingConstants.RIGHT);
    setBorder(BorderFactory.createLineBorder(Color.BLACK));
  }
}
