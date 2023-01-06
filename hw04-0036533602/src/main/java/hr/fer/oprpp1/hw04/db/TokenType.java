package hr.fer.oprpp1.hw04.db;

/**
 *
 * Token types
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public enum TokenType {

    /**
     * Comparison operators < , > , = , !=, LIKE, >= <=
     */
    COMPARISON_OP,
    /**
     * String
     */
    STRING_LITERAL,
    /**
     * Attribute in table
     */
    ATTRIBUTE,
    /**
     * Logical operators
     */
    LOGICAL_OP,
    /**
     * End of file
     */
    EOF
}
