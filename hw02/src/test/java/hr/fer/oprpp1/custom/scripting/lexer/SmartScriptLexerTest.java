package hr.fer.oprpp1.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * SmartScriptLexer tests from problem 3
 *
 * @author Dean Trkulja
 * @version 1.0
 *
 */

public class SmartScriptLexerTest {

    /**
     * Test empty string
     */
    @Test
    public void testNotNull() {
         SmartScriptLexer smartScriptLexer = new SmartScriptLexer("");
         smartScriptLexer.nextToken();
         assertEquals(TokenType.EOF, smartScriptLexer.getToken().getType());
    }

    /**
     * Test if throws NullPointerException when passing null to constructor
     */
    @Test
    public void testNullInput() {
        assertThrows(NullPointerException.class, () -> new SmartScriptLexer(null));
    }

    @Test
    public void testGetSameToken(){
        SmartScriptLexer smartScriptLexer = new SmartScriptLexer("");
        smartScriptLexer.nextToken();
        assertEquals(TokenType.EOF, smartScriptLexer.getToken().getType());
        assertEquals(TokenType.EOF, smartScriptLexer.getToken().getType());
    }

    /**
     * Tests if it throws exception when we call nextToken after EOF token
     */
    @Test
    public void testNextTokenAfterEOF(){
        SmartScriptLexer smartScriptLexer = new SmartScriptLexer("");
        smartScriptLexer.nextToken();
        assertThrows(LexerException.class, smartScriptLexer::nextToken);
    }

    /**
     * Test lexer in Basic mode
     */

    @Test
    public void testBasicLexerMode(){
        String input ="Dean \n\t Trkulja";
        SmartScriptLexer smartScriptLexer = new SmartScriptLexer(input);
        smartScriptLexer.nextToken();
        assertEquals(input, smartScriptLexer.getToken().getValue());
    }

    /**
     * Tests if it throws exception for invalid escape expression in BASIC lexer mode
     */
    @Test
    public void testInvalidBasicLexerModeEscape(){
        SmartScriptLexer smartScriptLexer = new SmartScriptLexer("\\");
        assertThrows(LexerException.class, smartScriptLexer::nextToken);
    }
    /**
     * Tests if doesn't throw exception for valid escape expression in BASIC lexer mode
     */
    @Test
    public void testValidBasicLexerModeEscape(){
        SmartScriptLexer smartScriptLexer = new SmartScriptLexer("\\{");
        assertDoesNotThrow(smartScriptLexer::nextToken);
    }

    /**
     * Tests if it throws exception for invalid escape expression in TAG lexer mode
     */

    @Test
    public void testInvalidTagLexerModeEscape(){
        SmartScriptLexer smartScriptLexer = new SmartScriptLexer("\\");
        smartScriptLexer.setState(LexerState.TAG);
        assertThrows(LexerException.class, smartScriptLexer::nextToken);
    }

    /**
     * Tests if doesn't throw exception for valid escape expression in TAG lexer mode
     */

    @Test
    public void testValidTagLexerModeEscape(){
        SmartScriptLexer smartScriptLexer = new SmartScriptLexer("a\\\\");
        smartScriptLexer.setState(LexerState.TAG);
        assertDoesNotThrow(smartScriptLexer::nextToken);
    }

    /**
     * Tests if negative number is read and stored correctly
     */
    @Test
    public void testNegativeNumber(){
        SmartScriptLexer smartScriptLexer = new SmartScriptLexer("-2");
        smartScriptLexer.setState(LexerState.TAG);
        smartScriptLexer.nextToken();
        assertEquals(TokenType.INTEGER, smartScriptLexer.getToken().getType());
        assertEquals(-2, smartScriptLexer.getToken().getValue());
    }
    /**
     * Tests if double number is read and stored correctly
     */
    @Test
    public void testDoubleNumber(){
        SmartScriptLexer smartScriptLexer = new SmartScriptLexer("-2.1");
        smartScriptLexer.setState(LexerState.TAG);
        smartScriptLexer.nextToken();
        assertEquals(TokenType.DOUBLE, smartScriptLexer.getToken().getType());
        assertEquals(-2.1, smartScriptLexer.getToken().getValue());
    }

    /**
     * Tests if it throws exception for invalid number
     */
    @Test
    public void testInvalidNumber(){
        SmartScriptLexer smartScriptLexer = new SmartScriptLexer("-2.2.2");
        smartScriptLexer.setState(LexerState.TAG);
        assertThrows(LexerException.class, smartScriptLexer::nextToken);
    }

    /**
     * Tests if valid function name is read and stored correctly
     */
    @Test
    public void testValidFunctionName(){
        SmartScriptLexer smartScriptLexer = new SmartScriptLexer("@function");
        smartScriptLexer.setState(LexerState.TAG);
        smartScriptLexer.nextToken();
        assertEquals(TokenType.FUNCTION, smartScriptLexer.getToken().getType());
        assertEquals("@function", smartScriptLexer.getToken().getValue());
    }

    /**
     * Tests if throws exception for invalid function name
     */
    @Test
    public void testInvalidFunctionName(){
        SmartScriptLexer smartScriptLexer = new SmartScriptLexer("@1Function");
        smartScriptLexer.setState(LexerState.TAG);
        assertThrows(LexerException.class, smartScriptLexer::nextToken);
    }

    /**
     * Tests if operator is read and stored properly
     */
    @Test
    public void testOperator(){
        SmartScriptLexer smartScriptLexer = new SmartScriptLexer("+");
        smartScriptLexer.setState(LexerState.TAG);
        smartScriptLexer.nextToken();
        assertEquals(TokenType.OPERATOR, smartScriptLexer.getToken().getType());
        assertEquals('+', smartScriptLexer.getToken().getValue());
    }

    /**
     * Tests if it throws exception when setting state of lexer to null
     */
    @Test
    public void testSetStateNull(){
        SmartScriptLexer smartScriptLexer = new SmartScriptLexer("dasd");
        assertThrows(NullPointerException.class, () -> smartScriptLexer.setState(null));
    }
}
