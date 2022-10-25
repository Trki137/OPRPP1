package hr.fer.oprpp1.hw02.prob1;

/**
 * Represent every exception that has something to do with our SmartScriptLexer
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public class LexerException extends RuntimeException{

    /**
     * Default constructor for LexerException
     */

    public LexerException(){
        super();
    }

    /**
     * Constructor with msg for LexerException
     */

    public LexerException(String msg){
        super(msg);
    }
}
