package hr.fer.zemris.java.gui.calc.components;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

/**
 * {@code BinaryButton} class represents a button that when pressed executes binary operation
 * It extends {@link CalcJButton}
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public class BinaryButton extends CalcJButton{

  /**
   * Creates new <code>BinaryButton</code>
   * @param text text on button
   * @param operator binary operation to execute
   * @param model calc model
   * @throws NullPointerException if <code>operator</code> or <code>model</code> are null
   */
  public BinaryButton(String text, DoubleBinaryOperator operator, CalcModel model) {
    super(text,e ->{
      Objects.requireNonNull(model);
      Objects.requireNonNull(operator);

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
      model.setPendingBinaryOperation(operator);
    });
  }
}
