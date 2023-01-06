package hr.fer.zemris.math;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ComplexTest {

  private static final double DELTA = 1E-8;

  @Test
  public void testModule() {
    Complex c = new Complex(3, 4);
    assertEquals(5, c.module(), DELTA);
  }

  @Test
  public void  testPower(){
    Complex c = new Complex(1,Math.sqrt(3));
    assertEquals(new Complex(64,0), c.power(6));
  }

  @Test
  public void testMultiply() {
    Complex c = new Complex(3, 4).multiply(new Complex(5, 6));
    assertEquals(-9, c.getRe(), DELTA);
    assertEquals(38, c.getIm(), DELTA);
  }

  @Test
  public void testDivide() {
    Complex c = new Complex(3, 4).divide(new Complex(5, 6));
    assertEquals(39.0 / 61, c.getRe(), DELTA);
    assertEquals(2.0 / 61, c.getIm(), DELTA);
  }

  @Test()
  public void testDivideByZero() {
    Complex c = new Complex(3, 4);
    Complex other = new Complex();

    assertThrows(IllegalArgumentException.class,() -> c.divide(other));
  }

  @Test
  public void testAdd() {
    Complex c = new Complex(3, 4).add(new Complex(5, 6));
    assertEquals(8, c.getRe(), DELTA);
    assertEquals(10, c.getIm(), DELTA);
  }

  @Test
  public void testSub() {
    Complex c = new Complex(3, 4).sub(new Complex(5, 6));
    assertEquals(-2, c.getRe(), DELTA);
    assertEquals(-2, c.getIm(), DELTA);
  }

  @Test
  public void testNegate() {
    Complex c = new Complex(3, 4).negate();
    assertEquals(-3, c.getRe(), DELTA);
    assertEquals(-4, c.getIm(), DELTA);
  }

  @Test
  public void testPower2() {
    Complex c = new Complex(3, 4).power(6);
    assertEquals(11753, c.getRe(), DELTA);
    assertEquals(-10296, c.getIm(), DELTA);
  }

 @Test
  public void testPowerLessThanZero() {
    Complex c = new Complex(3, 4);
    assertThrows(IllegalArgumentException.class, () -> c.power(-6));
  }


  @Test
  public void testRoot() {
    List<Complex> roots = new Complex(3, 4).root(4);

    assertEquals(1.455346690225355, roots.get(0).getRe(), DELTA);
    assertEquals(0.34356074972251244, roots.get(0).getIm(), DELTA);
    assertEquals(-0.34356074972251244, roots.get(1).getRe(), DELTA);
    assertEquals(1.455346690225355, roots.get(1).getIm(), DELTA);
    assertEquals(-1.455346690225355, roots.get(2).getRe(), DELTA);
    assertEquals(-0.34356074972251244, roots.get(2).getIm(), DELTA);
    assertEquals(0.34356074972251244, roots.get(3).getRe(), DELTA);
    assertEquals(-1.455346690225355, roots.get(3).getIm(), DELTA);
  }

  @Test
  public void testRootLessThan1() {
    Complex c = new Complex(3, 4);
    assertThrows(IllegalArgumentException.class, () -> c.root(-6));
  }

  @Test
  public void testParseZero(){
    assertEquals(Complex.ZERO, Complex.parse("0"));
    assertEquals(Complex.ZERO, Complex.parse("i0"));
    assertEquals(Complex.ZERO, Complex.parse("0 + i0"));
    assertEquals(Complex.ZERO, Complex.parse("0 - i0"));
  }

  @Test
  public void testParseRealOneImZero(){
    assertEquals(Complex.ONE, Complex.parse("1"));
    assertEquals(Complex.ONE,Complex.parse("1 + i0"));
    assertEquals(Complex.ONE,Complex.parse("1 - i0"));
  }

  @Test
  public void testParseRealNegOneImZero(){
    assertEquals(Complex.ONE_NEG, Complex.parse("-1"));
    assertEquals(Complex.ONE_NEG,Complex.parse("-1 + i0"));
    assertEquals(Complex.ONE_NEG,Complex.parse("-1 - i0"));
  }

  @Test
  public void testParseRealZeroImOne(){
    assertEquals(Complex.IM, Complex.parse("i1"));
    assertEquals(Complex.IM, Complex.parse("i"));
    assertEquals(Complex.IM,Complex.parse("0 + i"));
    assertEquals(Complex.IM,Complex.parse("0 + i1"));
  }

  @Test
  public void testParseRealZeroImNegOne(){
    assertEquals(Complex.IM_NEG, Complex.parse("-i1"));
    assertEquals(Complex.IM_NEG,Complex.parse("0 - i1"));
  }

  @Test
  public void testParse(){
    assertEquals(new Complex(1,1), Complex.parse("1 + i1"));
    assertEquals(new Complex(2,3), Complex.parse("2 + i3"));

    assertEquals(new Complex(1,-1), Complex.parse("1 - i1"));
    assertEquals(new Complex(1,-3), Complex.parse("1 - i3"));

    assertEquals(new Complex(-1,1), Complex.parse("-1 + i1"));
    assertEquals(new Complex(-5,10), Complex.parse("-5 + i10"));

    assertEquals(new Complex(-1,-1), Complex.parse("-1 - i1"));
    assertEquals(new Complex(1,-41), Complex.parse("1 - i41"));


  }

}