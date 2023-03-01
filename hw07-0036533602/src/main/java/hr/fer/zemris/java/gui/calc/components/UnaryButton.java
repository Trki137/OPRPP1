package hr.fer.zemris.java.gui.calc.components;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

import java.util.Objects;
import java.util.function.DoubleUnaryOperator;

/**
 * {@code UnaryButton} class represents a button that when pressed executes one of unary operation, depends on if operation is inverted or not.
 * It extends {@link CalcJButton}
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public class UnaryButton extends CalcJButton{

  /**
   * Current model
   */
  private final CalcModel calcModel;

  /**
   * Text on button when inverted
   */
  private final String invertedText;

  /**
   * Creates new <code>UnaryButton</code>
   *
   * @param text text on button
   * @param operator unary operation when not inverted
   * @param invertedOperator operation when inverted
   * @param calcModel calculator model
   * @throws NullPointerException if <code>operator</code> or <code>invertedOperator</code> or <code>model</code> are null
   */
  public UnaryButton(String text, String invertedText,DoubleUnaryOperator operator, DoubleUnaryOperator invertedOperator, CalcModel calcModel) {
    super(text ,e -> {
      Objects.requireNonNull(operator);
      Objects.requireNonNull(invertedOperator);
      Objects.requireNonNull(calcModel);

      DoubleUnaryOperator currentOperator = calcModel.isInvert() ? invertedOperator : operator;
      double result;
      if(calcModel.isActiveOperandSet()) result = currentOperator.applyAsDouble(calcModel.getActiveOperand());
      else result = currentOperator.applyAsDouble(calcModel.getValue());

      calcModel.setValue(result);
    });

    this.calcModel = calcModel;
    this.invertedText = invertedText;
  }

  /**
   * Updates text on button based on if model is inverted or not
   */
  public void updateText(){
    setText(calcModel.isInvert() ? invertedText : text);
  }
}
