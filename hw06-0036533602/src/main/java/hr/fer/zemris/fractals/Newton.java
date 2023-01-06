package hr.fer.zemris.fractals;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <code>Newton</code> class contains implementation of a Newton-Raphson iteration-based fractal viewer.
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public class Newton {

  /**
   * Entry point for program that executes Newton-Raphson fractal viewer.
   *
   * @param args an array of command-line arguments.
   * @throws IllegalArgumentException if user doest enter at least 2 roots
   */

  public static void main(String[] args) {
    System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
    System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

    String terminateKeyWord = "done";
    Scanner sc = new Scanner(System.in);

    List<Complex> roots = new ArrayList<>();

    while (true){
      System.out.printf("Root %d> ",roots.size() + 1);
      String input = sc.nextLine().trim();

      if(input.isBlank()){
        System.out.println("Input can't be blank");
        continue;
      }

      if(input.equals(terminateKeyWord)){
        if(roots.size() < 2) throw new IllegalArgumentException("You must provide minimum 2 roots");
        break;
      }

      roots.add(Complex.parse(input));
    }

    System.out.println("Image of fractal will appear shortly. Thank you.");

    NewtonProducer newtonProducer = new NewtonProducer(new ComplexRootedPolynomial(Complex.ONE, roots.toArray(Complex[]::new)));
    FractalViewer.show(newtonProducer);
  }

  /**
   * <code>NewtonProducer</code> represent an implementation of <link>IFractalProducer</link>
   *
   * @author Dean Trkulja
   * @version 1.0
   */
  private static class NewtonProducer implements IFractalProducer{
    /**
     * Polynomial used for executing Newton-Raphson fractal viewer
     */
    private final ComplexRootedPolynomial complexRootedPolynomial;

    /**
     * Complex polynomial from <code>complexRootedPolynomial</code>
     */
    private final ComplexPolynomial complexPolynomial;

    /**
     * First derivation of <code>complexPolynomial</code>
     */

    private final ComplexPolynomial firstDerivative;

    /**
     * Creates an instance of <code>NewtonProducer</code>
     *
     * @param complexRootedPolynomial polynomial used for executing Newton-Raphson fractal viewer
     */
    public NewtonProducer(ComplexRootedPolynomial complexRootedPolynomial){
      this.complexRootedPolynomial = complexRootedPolynomial;
      this.complexPolynomial = complexRootedPolynomial.toComplexPolynom();
      this.firstDerivative = complexPolynomial.derive();
    }

    @Override
    public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo, IFractalResultObserver observer,
                        AtomicBoolean cancel) {

      System.out.println("Započinjem izračun...");


      int m = 16 * 16;
      double rootThreshold = 1E-3;
      double convergenceThreshold = 0.002;
      int offset = 0;
      short[] data = new short[width * height];

      for(int y = 0; y < height; y++){
        if(cancel.get()) break;

        for(int x = 0; x < width; x++){
          double cRe = x / (width - 1.0) * (reMax - reMin) + reMin;
          double cIm = (height - 1.0 - y) / (height - 1.0) *(imMax - imMin) + imMin;
          double module;
          Complex zn = new Complex(cRe,cIm);
          int iters = 0;
          do{
            Complex numerator = complexPolynomial.apply(zn);
            Complex denominator = firstDerivative.apply(zn);
            Complex znoId = zn;
            Complex fraction = numerator.divide(denominator);
            zn = zn.sub(fraction);
            module = zn.sub(znoId).module();
            iters++;
          }while(module > convergenceThreshold && iters < m);

          int index = this.complexRootedPolynomial.indexOfClosestRootFor(zn, rootThreshold);
          data[offset++] = (short)(index + 1);
        }

        System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
        observer.acceptResult(data, (short)(complexPolynomial.order() + 1), requestNo);

      }
    }
  }
}
