package hr.fer.oprpp1.hw02.prob1;

/**
 *
 * Class that extends LexerException.
 * Represents exceptions when we cant tokenize some text
 *
 * @author Dean Trkulja
 * @version 1.0
 *
 */
public class LexerTokenizerException extends LexerException {
    /**
     * Default constructor for LexerTokenizerException
     */
    public LexerTokenizerException(){
        super();
    }

    /**
     * Constructor for LexerTokenizerException with message
     *
     * @param msg error message
     */
    public LexerTokenizerException(String msg){
        super(msg);
    }
}
