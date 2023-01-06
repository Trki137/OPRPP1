package hr.fer.oprpp1.hw04.db;

/**
 * Exception class for our lexer
 *
 * @author Dean Trkulja
 * @version 1.0
 */

public class QueryLexerException extends RuntimeException {

    /**
     * Constructor with message
     *
     * @param msg message
     */
    public QueryLexerException(String msg){
        super(msg);
    }
}
