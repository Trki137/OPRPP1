package hr.fer.oprpp1.hw02.prob1;

/**
 * Represents token which is used in lexer
 *
 * @author Dean Trkulja
 * @version 1.0
 *
 */
public class Token {

    /**
     *  Type of token
     */

    private TokenType type;
    /**
     * Value of token, it can be null
     */
    private Object value;

    /**
     * Constructor for Token class, value of token can be null
     *
     * @param type type of token
     * @param value value of token
     * @throws NullPointerException when TokenType is null
     */

    public Token(TokenType type, Object value){
        if(type == null) throw new NullPointerException("TokenType can't be null");

        this.type = type;
        this.value = value;
    }

    /**
     *
     * @return Object value of token
     */

    public Object getValue() {
        return value;
    }

    /**
     *
     * @return TokenType return type of token
     */

    public TokenType getType() {
        return type;
    }
}
