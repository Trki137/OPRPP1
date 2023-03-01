package hr.fer.zemris.java.gui.layouts;

/**
 * <code>CalcLayoutException</code> class represent exception that are produced by  {@link CalcLayout} class.
 * Implements {@link RuntimeException}
 *
 * @author Dean Trkulja
 * @version 1.0
 */

public class CalcLayoutException extends RuntimeException{
  /**
   * Empty constructor for <code>CalcLayoutException</code>
   */
  public CalcLayoutException(){
    super();
  }

  /**
   * Constructor with message for <code>CalcLayoutException</code>
   * @param message error message
   */
  public CalcLayoutException(String message){
    super(message);
  }
}
