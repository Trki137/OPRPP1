package hr.fer.zemris.math;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ComplexRootedPolynomialTest {
  @Test
  public void apply() {
    ComplexRootedPolynomial c =new ComplexRootedPolynomial(new Complex(5, 2), new Complex(3, 4), new
        Complex(1, 1));
    Complex c1 = c.apply(new Complex(3, 3));

    assertEquals(new Complex(14, -6), c1);
  }

  @Test
  public void toComplexPolynom() {
    ComplexPolynomial polynomial = new ComplexRootedPolynomial(Complex.ONE,new Complex(5, 2), new Complex(3, 4), new
        Complex(1, 1)).toComplexPolynom();

    Complex[] factors = polynomial.getFactors();

    assertEquals(new Complex(19, -33), factors[0]);
    assertEquals(new Complex(9, 40), factors[1]);
    assertEquals(new Complex(-9, -7), factors[2]);
    assertEquals(new Complex(1, 0), factors[3]);
  }

  @Test
  public void indexOfClosestRootFor() {
    ComplexRootedPolynomial c = new ComplexRootedPolynomial(new Complex(5, 2), new Complex(3, 4), new Complex(1, 1));
    int closestRoot = c.indexOfClosestRootFor(new Complex(3.0000001, 4), 1E-3);

    assertEquals(0, closestRoot);
  }

  @Test
  public void indexOfClosestRootForNoClosest() {
    int closestRoot = new ComplexRootedPolynomial(new Complex(5, 2), new Complex(3, 4), new
        Complex(1, 1)).indexOfClosestRootFor(new Complex(20.0000001, 4), 1E-3);

    assertEquals(-1, closestRoot);
  }
}
