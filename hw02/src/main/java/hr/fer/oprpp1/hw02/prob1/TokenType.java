package hr.fer.oprpp1.hw02.prob1;

/**
 * Type of tokens for lexer
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public enum TokenType {
    /**
     * End of file
     */
    EOF,
    /**
     * String of letters without numbers
     */
    WORD,
    /**
     * Numbers type Long
     */
    NUMBER,
    /**
     * Includes symbols like . , : ; etc...
     */
    SYMBOL;
}
