package hr.fer.oprpp1.hw04.db;

import java.util.Arrays;
import java.util.List;

/**
 * Lexer for our query
 *
 * @author Dean Trkulja
 * @version 1.0
 */

public class QueryLexer {

    /**
     * Current token in lexer
     */
    private Token currentToken;

    /**
     * Where to start for next token
     */
    private int position;
    /**
     * Input text document converted into char array
     */
    private final char[] input;

    /**
     * All valid comparison operators
     */
    private final List<String> comparisonOperators;

    /**
     * All valid atribute fields
     */
    private final List<String> atributeFields;

    /**
     * Constructor for QueryLexer
     *
     * @param textDocument text that we need put through lexer
     */
    public QueryLexer(String textDocument){
        this.currentToken = null;
        this.position = 0;
        this.comparisonOperators = Arrays.asList(">",">=","<","<=","=","!=","LIKE");
        this.atributeFields = Arrays.asList("lastName", "firstName", "jmbag");
        this.input = textDocument.toCharArray();
    }

    /**
     * Returns current token
     *
     * @return Token current token
     */

    public Token getCurrentToken(){
        return currentToken;
    }


    /**
     * Generates next token if there is one
     *
     * @throws QueryLexerException if there is no more tokens to read
     */
    public void nextToken(){
        if(currentToken != null &&
                currentToken.getTokenType() == TokenType.EOF)
            throw new QueryLexerException("There is no more tokens to read");

        escapeSpaceCharacters();

        if(position == input.length) {
            currentToken = new Token(TokenType.EOF);
            return;
        }

        readQuery();

    }

    /**
     * Helper method to determine what kind of token we need to generate
     */

    private void readQuery(){
        if(input[position] == '"'){
            position++;
            readStringLiteral();
            return;
        }
        if(input[position] == 'L') {
            readOperator();
            return;
        }
        if(Character.toUpperCase(input[position]) == 'A'){
            readLogicalOperator();
            return;
        }
        if(comparisonOperators.contains(String.valueOf(input[position])) || input[position] == '!') readOperator();
        else readAtribute();
    }

    /**
     * Creates attribute token.
     * @throws QueryLexerException when atribute name is invalid
     */
    private void readAtribute(){
        StringBuilder sb = new StringBuilder();
        while(position < input.length){

            if(input[position] == ' ' || comparisonOperators.contains(String.valueOf(input[position])))
                break;

            sb.append(input[position++]);

        }

        if(!atributeFields.contains(sb.toString()))
            throw new QueryLexerException("Invalid atribute name. Given atribute name: "+ sb);

        currentToken = new Token(TokenType.ATTRIBUTE, sb.toString());


    }

    /**
     * Creates operator token.
     * @throws QueryLexerException when opeartor is not from list {@code comparisonOperators}
     */
    private void readOperator(){
        StringBuilder sb = new StringBuilder();

        while (input[position] != '"' && input[position] != ' ') sb.append(input[position++]);

        if(!comparisonOperators.contains(sb.toString().trim()))
            throw new QueryLexerException("Invalid comparison operator. "+ sb + " is not valid comparison operator");

        currentToken = new Token(TokenType.COMPARISON_OP, sb.toString());

    }

    /**
     * Creates string literal token.
     * @throws QueryLexerException when string literal is not closed appropriately
     */
    private void readStringLiteral(){
        StringBuilder sb = new StringBuilder();

        while(position < input.length) {
            if(position + 1 >= input.length && input[position] != '"')
                throw new QueryLexerException("Invalid string literal. There is no closing \"");

            if(input[position] == '"') {
                position++;
                break;
            }

            sb.append(input[position++]);
        }

        if(currentToken.getValue().toString().equals("LIKE"))
            if(!checkValidInCaseLike(sb.toString()))
                throw new QueryLexerException("* can appear max 1 time");


        currentToken = new Token(TokenType.STRING_LITERAL, sb.toString());
    }

    /**
     * If comparison operator is LIKE it checks if literal has valid number of *.
     *
     * @param literal string to check
     * @return true if number of * is between 0 and 1, else false
     */

    public boolean checkValidInCaseLike(String literal){
        if(literal.contains("*"))
            return literal.indexOf("*") == literal.lastIndexOf("*");

        return true;
    }

    /**
     * Creates logical operator token.
     * @throws QueryLexerException if is not logical operator AND, case is ignored
     */
    private void readLogicalOperator(){
        StringBuilder sb =  new StringBuilder();

        while(position < input.length && input[position] != ' ') sb.append(input[position++]);

        if(!sb.toString().equalsIgnoreCase("AND"))
            throw new QueryLexerException("Invalid logical operator. Expected AND but got: "+ sb);

        currentToken = new Token(TokenType.LOGICAL_OP,sb.toString());
    }


    /**
     * Method for skipping blanks
     */

    private void escapeSpaceCharacters(){
        while (position < input.length){
            if(input[position] != ' ' && input[position] != '\t' && input[position] != '\n') break;

            position++;

        }

    }
}
