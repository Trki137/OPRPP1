package hr.fer.oprpp1.hw04.db;

/**
 * Exception class for our parser
 * @author Dean Trkulja
 * @version 1.0
 */
public class QueryParserException extends RuntimeException{

    /**
     * Constructor for QueryParserException
     *
     * @param msg text that is send with exception
     */
    public QueryParserException(String msg){
        super(msg);
    }
}
