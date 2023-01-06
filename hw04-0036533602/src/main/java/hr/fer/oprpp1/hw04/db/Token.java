package hr.fer.oprpp1.hw04.db;

/**
 * Represent tokens that our lexer uses
 *
 * @author Dean Trkulja
 * @version 1.0
 */

public class Token {
    /**
     * Type of token
     */
    private final TokenType tokenType;

    /**
     * Value of token, it can be null
     */
    private final Object value;

    /**
     * Constructor for token that delegates to another constructor
     *
     * @param tokenType type of token
     * @throws NullPointerException if {@param type} is null
     */

    public Token(TokenType tokenType){
        this(tokenType, null);
    }

    /**
     * Constructor for token
     *
     * @param tokenType type of token
     * @param value value of token
     * @throws NullPointerException if {@param type} is null
     */

    public Token(TokenType tokenType,Object value){
        if(tokenType == null) throw new NullPointerException("Token type ca't be null");

        this.tokenType = tokenType;
        this.value = value;
    }

    /**
     * Returns token value
     * @return value token value
     */
    public Object getValue() {
        return value;
    }

    /**
     * Returns token type
     * @return TokenType type of token
     */

    public TokenType getTokenType() {
        return tokenType;
    }

    @Override
    public String toString() {
        return tokenType.toString();
    }
}
