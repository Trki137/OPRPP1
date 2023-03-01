package hr.fer.zemris.java.gui.calc;


import hr.fer.zemris.java.gui.calc.components.*;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

/**
 * <code>Calculator</code> class represent a calculator swing application
 * <code>Calculator</code> extends {@link JFrame}
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public class Calculator extends JFrame {

  /**
   * Calculator model
   */
  private final CalcModelImpl calcModel;

  /**
   * Holds all buttons that have an inverse function
   */

  private final List<CalcJButton> inverseButtonList = new ArrayList<>();

  /**
   * Stack for options
   */
  private final Stack<Double> stack;

  /**
   * Creates new <code>Calculator</code>
   */
  public Calculator(){
    calcModel = new CalcModelImpl();
    this.stack = new Stack<>();
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setSize(700,300);
    setTitle("Java Calculator v1.0");
    initGUI();

  }

  /**
   * Initializes graphical interface
   */
  private void initGUI(){
    Container container = getContentPane();
    container.setLayout(new CalcLayout(5));

    addDisplay(container);
    addDigitBtn(container);
    addBinaryBtn(container);
    addUnaryBtn(container);
    addOptionsBtn(container);
    addCheckBox(container);

  }

  /**
   * Adds checkbox to calculator
   *
   * @param container parent container
   */
  private void addCheckBox(Container container) {
    Objects.requireNonNull(container);

    JCheckBox checkBox = new JCheckBox("Inv");
    checkBox.addActionListener(e -> {
      calcModel.invert();
      inverseButtonList.forEach(CalcJButton::updateText);
    });
    container.add(checkBox, new RCPosition(5,7));
  }

  /**
   * Adds options buttons to calculator
   *
   * @param container parent container
   */
  private void addOptionsBtn(Container container) {
    Objects.requireNonNull(container);
    container.add(new OptionsButton("push", e -> {

      double currentValue = calcModel.getValue();
      stack.push(currentValue);

      double result = calcModel.getPendingBinaryOperation().applyAsDouble(calcModel.getActiveOperand(), calcModel.getValue());

      calcModel.freezeValue(String.valueOf(result));
      calcModel.setActiveOperand(result);
      calcModel.setPendingBinaryOperation(null);
      calcModel.clear();

    }), new RCPosition(3,7));
    container.add(new OptionsButton("pop", e -> {
      if(stack.empty())
        throw new CalculatorInputException("Cannot pop from an empty stack!");

      calcModel.setValue(stack.pop());
    }), new RCPosition(4,7));

    container.add(new OptionsButton("=", e -> {
      double res = calcModel.getPendingBinaryOperation().applyAsDouble(calcModel.getActiveOperand(), calcModel.getValue());
      calcModel.freezeValue(String.valueOf(res));
      calcModel.setActiveOperand(res);
      calcModel.setPendingBinaryOperation(null);
      calcModel.clear();
    }), new RCPosition(1,6));
    container.add(new OptionsButton("+/-", e -> calcModel.swapSign()), new RCPosition(5,4));
    container.add(new OptionsButton("clr", e -> calcModel.clear()), new RCPosition(1,7));
    container.add(new OptionsButton("res", e -> calcModel.clearAll()),new RCPosition(2,7));
    container.add(new OptionsButton(".", e -> calcModel.insertDecimalPoint()), new RCPosition(5,5));

  }
  /**
   * Adds unary buttons to calculator
   *
   * @param container parent container
   */

  private void addUnaryBtn(Container container) {
    Objects.requireNonNull(container);
    UnaryButton unaryButtonSin = new UnaryButton("sin", "arcsin" ,Math::sin, Math::asin, calcModel);
    UnaryButton unaryButtonCos = new UnaryButton("cos", "arccos",Math::cos, Math::acos,calcModel);
    UnaryButton unaryButtonTg = new UnaryButton("tan", "arctan",Math::tan, Math::atan,calcModel);
    UnaryButton unaryButtonCtg = new UnaryButton("ctg", "arcctg",v -> Math.atan(1/v), v -> 1 / Math.atan(v),calcModel);
    UnaryButton unaryButtonE = new UnaryButton("e^x", "ln",v -> Math.pow(Math.E, v), Math::log,calcModel);
    UnaryButton unaryButtonBase10 = new UnaryButton("10^x", "log",v -> Math.pow(10,v), Math::log10, calcModel);
    UnaryButton unaryButtonProportional = new UnaryButton("1/x", "1/x",v -> 1 /v, v -> 1/v, calcModel);

    inverseButtonList.add(unaryButtonSin);
    inverseButtonList.add(unaryButtonCos);
    inverseButtonList.add(unaryButtonTg);
    inverseButtonList.add(unaryButtonCtg);
    inverseButtonList.add(unaryButtonE);
    inverseButtonList.add(unaryButtonBase10);
    inverseButtonList.add(unaryButtonProportional);

    container.add(unaryButtonSin, new RCPosition(2,2));
    container.add(unaryButtonCos, new RCPosition(3,2));
    container.add(unaryButtonTg, new RCPosition(4,2));
    container.add(unaryButtonCtg,new RCPosition(5,2));
    container.add(unaryButtonProportional, new RCPosition(2,1));
    container.add(unaryButtonBase10, new RCPosition(3,1));
    container.add(unaryButtonE, new RCPosition(4,1));
  }

  /**
   * Adds binary buttons to calculator
   *
   * @param container parent container
   */
  private void addBinaryBtn(Container container) {
    Objects.requireNonNull(container);

    container.add(new BinaryButton("/", (left, right) -> left / right,calcModel), new RCPosition(2,6));
    container.add(new BinaryButton("*", (left, right) -> left * right,calcModel), new RCPosition(3,6));
    container.add(new BinaryButton("-", (left, right) -> left - right,calcModel), new RCPosition(4,6));
    container.add(new BinaryButton("+", Double::sum,calcModel), new RCPosition(5,6));

    BinnaryInvertButton binnaryInvertButton = new BinnaryInvertButton("x^n","x^(1/n)", (Math::pow),((left, right) -> Math.pow(left, 1/right)),calcModel);
    inverseButtonList.add(binnaryInvertButton);
    container.add(binnaryInvertButton, new RCPosition(5,1));

  }

  /**
   * Adds digit buttons to calculator
   *
   * @param container parent container
   */
  private void addDigitBtn(Container container) {
    Objects.requireNonNull(container);
    container.add(new DigitButton("7", e -> {
      calcModel.freezeValue(null);
      calcModel.insertDigit(7);}
    ), new RCPosition(2,3));
    container.add(new DigitButton("8",e -> {
      calcModel.freezeValue(null);
      calcModel.insertDigit(8);}
    ), new RCPosition(2,4));
    container.add(new DigitButton("9",e -> {
      calcModel.freezeValue(null);
      calcModel.insertDigit(9);
    }), new RCPosition(2,5));
    container.add(new DigitButton("4",e -> {
      calcModel.freezeValue(null);
      calcModel.insertDigit(4);}), new RCPosition(3,3));
    container.add(new DigitButton("5",e -> {
      calcModel.freezeValue(null);
      calcModel.insertDigit(5);}), new RCPosition(3,4));
    container.add(new DigitButton("6",e -> {
      calcModel.freezeValue(null);
      calcModel.insertDigit(6);}), new RCPosition(3,5));
    container.add(new DigitButton("1",e -> {
      calcModel.freezeValue(null);
      calcModel.insertDigit(1);}), new RCPosition(4,3));
    container.add(new DigitButton("2",e -> {
      calcModel.freezeValue(null);
      calcModel.insertDigit(2);}), new RCPosition(4,4));
    container.add(new DigitButton("3",e -> {
      calcModel.freezeValue(null);
      calcModel.insertDigit(3);}), new RCPosition(4,5));
    container.add(new DigitButton("0",e -> {
      calcModel.freezeValue(null);
      calcModel.insertDigit(0);}), new RCPosition(5,3));
  }

  /**
   * Adds calculator screen
   *
   * @param container parent container
   */
  private void addDisplay(Container container){
    Objects.requireNonNull(container);

    CalcScreen calcScreen = new CalcScreen(calcModel.toString());
    container.add(calcScreen, new RCPosition(1,1));
    calcModel.addCalcValueListener(calcModel -> calcScreen.setText(calcModel.toString()));
  }


  /**
   * Entry point that starts our swing application
   *
   * @param args an array of command-line arguments.
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new Calculator().setVisible(true));
  }
}
