package hr.fer.zemris.java.gui.calc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

/**
 * <code>CalcModelImpl</code> class represent an implementation of {@link CalcModel}
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public class CalcModelImpl implements CalcModel{
  /**
   * Is calc editable
   */
  private boolean isEditable;

  /**
   * Is number negative
   */
  private boolean isNegative;

  /**
   * Is checkbox checked
   */
  private boolean isInvert;

  /**
   * Users current input
   */
  private String currentInput;

  /**
   * Users current input
   */
  private Double currentValue;

  /**
   * Active operand
   */

  private Double activeOperand;

  /**
   * Next operation
   */

  private DoubleBinaryOperator pendingOperation;

  /**
   * Digits that were last set and to be shown on the calculator display.
   */
  private String frozenValue;

  /**
   * List of listeners
   */
  private final List<CalcValueListener> listeners;

  /**
   * Constructor that creates new <code>CalcModelImplementation</code>
   */
  public CalcModelImpl(){
    this.currentValue = 0.0;
    this.frozenValue = null;
    this.activeOperand = null;
    this.isNegative = false;
    this.isEditable = true;
    this.currentInput = "";
    this.pendingOperation = null;
    this.listeners = new ArrayList<>();
    this.isInvert = false;
  }

  @Override
  public void addCalcValueListener(CalcValueListener l) {
    Objects.requireNonNull(l);
    listeners.add(l);
  }

  @Override
  public void removeCalcValueListener(CalcValueListener l) {
    Objects.requireNonNull(l);
    listeners.remove(l);
  }

  @Override
  public double getValue() {
    return isNegative ? -currentValue : currentValue;
  }

  @Override
  public void setValue(double value) {
    currentValue = Math.abs(value);
    isNegative = value < 0;
    currentInput = String.valueOf(currentValue);
    isEditable = false;
    frozenValue = null;

    notifyListeners();
  }

  @Override
  public boolean isEditable() {
    return isEditable;
  }

  @Override
  public void clear() {
    this.isEditable = true;
    this.isNegative = false;
    currentInput = "";
    currentValue = null;

    notifyListeners();
  }

  @Override
  public void clearAll() {
    clear();
    activeOperand = null;
    pendingOperation = null;
    frozenValue = null;

    notifyListeners();
  }

  @Override
  public void swapSign() throws CalculatorInputException {
    if(!isEditable)
      throw new CalculatorInputException();

    isNegative = !isNegative;

    notifyListeners();
  }

  @Override
  public void insertDecimalPoint() throws CalculatorInputException {
    if(!isEditable)
      throw new CalculatorInputException();

    if(currentInput.isBlank())
      throw new CalculatorInputException("Invalid decimal number");

    if(currentInput.contains("."))
      throw new CalculatorInputException("Invalid decimal number");

    currentInput += ".";
    frozenValue = null;

    notifyListeners();
  }

  @Override
  public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {

    if(!isEditable)
      throw new CalculatorInputException("Calculator is not editable");

      if(Double.isInfinite(Double.parseDouble(currentInput + digit)))
        throw new CalculatorInputException("Number is not parsable");

      currentValue = Double.parseDouble(currentInput + digit);

      if(currentInput.equals("0") && digit == 0) return;

      if(currentInput.length() == 1 && currentInput.equals("0")){
        currentInput = String.valueOf(digit);
        return;
      }

      currentInput = currentInput + digit;

    notifyListeners();
  }

  @Override
  public boolean isActiveOperandSet() {
    return activeOperand != null;
  }

  @Override
  public double getActiveOperand() throws IllegalStateException {
    if(activeOperand == null)
      throw new IllegalStateException("Active operand not set");

    return activeOperand;
  }

  @Override
  public void setActiveOperand(double activeOperand) {
    this.activeOperand = activeOperand;
    this.isEditable = false;
    notifyListeners();
  }

  @Override
  public void clearActiveOperand() {
      this.activeOperand = null;

    notifyListeners();
  }

  @Override
  public DoubleBinaryOperator getPendingBinaryOperation() {
    return pendingOperation;
  }

  @Override
  public void setPendingBinaryOperation(DoubleBinaryOperator op) {
    this.pendingOperation = op;
  }

  @Override
  public void freezeValue(String value) {
    this.frozenValue = value;
  }

  @Override
  public boolean hasFrozenValue() {
    return this.frozenValue != null;
  }

  @Override
  public void invert() {
    this.isInvert = !this.isInvert;
    notifyListeners();
  }

  @Override
  public boolean isInvert() {
    return isInvert;
  }

  @Override
  public String toString() {
    if(frozenValue != null) return isNegative ? "-" + frozenValue : frozenValue;
    if(currentInput.isBlank() || currentInput.equals("0")) return isNegative ? "-" + "0" : "0";

    return isNegative ? "-" + currentInput : currentInput;
  }

  private void notifyListeners() {
    listeners.forEach(l -> l.valueChanged(this));
  }

}
