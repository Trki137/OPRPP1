package hr.fer.zemris.java.gui.calc.components;


import hr.fer.zemris.java.gui.calc.model.CalcModel;

import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

/**
 * {@code BinnaryInvertButton} class represents a button that when pressed executes one of two binary operation, depends on if it is in inverted state or not
 * It extends {@link CalcJButton}
 *
 * @author Dean Trkulja
 * @version 1.0
 */

public class BinnaryInvertButton extends CalcJButton {

  /**
   * Current model
   */
  private final CalcModel calcModel;

  /**
   * Text on button when inverted
   */
  private final String invertedText;

  /**
   * Creates new <code>BinnaryInvertButton</code>
   * @param text text on button
   * @param operator binary operation to execute when it's not inverted
   * @param invertedOperator binary operation to execute when it's inverted
   * @param model calc model
   * @throws NullPointerException if <code>operator</code> or <code>invertedOperator</code> or<code>model</code> are null
   */
  public BinnaryInvertButton(String text, String invertedText,DoubleBinaryOperator operator, DoubleBinaryOperator invertedOperator, CalcModel model) {
    super(text, e -> {
      Objects.requireNonNull(model);
      Objects.requireNonNull(operator);
      Objects.requireNonNull(invertedOperator);

      DoubleBinaryOperator activeOperator = model.getPendingBinaryOperation();

      if(activeOperator != null){
        double result = activeOperator.applyAsDouble(model.getActiveOperand(), model.getValue());
        model.setActiveOperand(result);
        model.freezeValue(String.valueOf(result));
      } else {
        if(!model.isActiveOperandSet())
          model.setActiveOperand(model.getValue());
      }

      model.clear();
      model.setPendingBinaryOperation(model.isInvert() ? invertedOperator : operator);
    });

    this.calcModel = model;
    this.invertedText = invertedText;
  }

  /**
   * Updates text on button based on if model is inverted or not
   */
  @Override
  public void updateText() {
    setText(calcModel.isInvert() ? invertedText: text);
  }
}
