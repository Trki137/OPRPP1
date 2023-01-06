package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser for our query
 *
 * @author Dean Trkulja
 * @version 1.0
 */

public class QueryParser {

    /**
     * Instance of lexer
     */
    private QueryLexer lexer;

    /**
     * List of all tokens created by lexer
     */
    private List<Token> allTokens;

    public QueryParser(String query){
        if(query == null) throw new NullPointerException();

        this.lexer = new QueryLexer(query);
        this.allTokens = parse();
    }

    /**
     * Creates list of all tokens with lexer
     * @return List<Token> list of all tokens with lexer
     */
    private List<Token> parse(){
        List<Token> tokens = new ArrayList<>();

        lexer.nextToken();
        while(lexer.getCurrentToken().getTokenType() != TokenType.EOF){
            tokens.add(lexer.getCurrentToken());
            lexer.nextToken();
        }

        tokens.add(lexer.getCurrentToken());

        return tokens;
    }

    /**
     * Checks if query is direct or not
     *
     * @return true if query is direct else false
     */

    public boolean isDirectQuery(){
        if(allTokens.size() != 4) return false;
        if(!allTokens.get(0).getValue().equals("jmbag")) return false;
        if(!allTokens.get(1).getValue().equals("=")) return false;

        return true;
    }

    /**
     * If query is direct then return jmbag
     * @return String jmbag from query
     * @throws QueryParserException when query is not direct
     */
    public String getQueriedJMBAG(){
        if(!isDirectQuery()) throw new QueryParserException("This query is not direct");

        return allTokens.get(allTokens.size() - 2).getValue().toString();
    }

    /**
     * Creates list of all conditional expressions from query.
     *
     * @return List<ConditionalExpression> list of all conditional expressions from query
     */
    public List<ConditionalExpression> getQuery(){
        List<ConditionalExpression> queries = new ArrayList<>();
        int index = 0;
        while(true){
            queries.add(new ConditionalExpression(
                    getFiledValueGetter(allTokens.get(index)),
                    getStringLiteral(allTokens.get(index + 2)),
                    getComparisonOperator(allTokens.get(index + 1))
            ));
            index += 3;

            if(allTokens.get(index).getTokenType() == TokenType.EOF) break;
            if(allTokens.get(index++).getTokenType() != TokenType.LOGICAL_OP)
                throw new QueryParserException("Invalid query statement. Expected logical operator but got " + allTokens.get(index).getTokenType());

        }

        return queries;
    }

    private String getStringLiteral(Token token) {
        if(token.getTokenType() != TokenType.STRING_LITERAL)
            throw new QueryParserException("Expected string literal but got "+ token.getTokenType());

        return token.getValue().toString();
    }

    /**
     * Determines what kind of operator is {@code param}
     *
     * @param token that we check what kind of comparison operator is it
     * @return IComparisonOperator one strategy from {@link ComparisonOperators}
     * @throws QueryParserException when tokenType is not COMPARISON_OP
     */
    private IComparisonOperator getComparisonOperator(Token token) {
        if(token.getTokenType() != TokenType.COMPARISON_OP)
            throw new QueryParserException("Invalid query statement. Expected comparison operator but got "+token.getTokenType());

        IComparisonOperator iComparisonOperator = switch (token.getValue().toString()){
            case "=" -> ComparisonOperators.EQUALS;
            case "!=" -> ComparisonOperators.NOT_EQUALS;
            case ">" -> ComparisonOperators.GREATER;
            case "<" -> ComparisonOperators.LESS;
            case ">=" -> ComparisonOperators.GREATER_OR_EQUALS;
            case "<=" -> ComparisonOperators.LESS_OR_EQUALS;
            case "LIKE" -> ComparisonOperators.LIKE;
            default -> null;
        };
        return iComparisonOperator;
    }

    /**
     * Determines which attribute will get from student record.
     *
     * @param token that we check what kind of comparison operator is it
     * @return IFieldValueGetter one strategy from {@link FieldValueGetters}
     * @throws QueryParserException when tokenType is not ATTRIBUTE
     */

    private IFieldValueGetter getFiledValueGetter(Token token) {
        if(token.getTokenType() != TokenType.ATTRIBUTE)
            throw new QueryParserException("Invalid query statement. Expected attribute but got "+token.getTokenType());

        IFieldValueGetter iFieldValueGetter = switch (token.getValue().toString()){
            case "firstName" -> FieldValueGetters.FIRST_NAME;
            case "lastName" -> FieldValueGetters.LAST_NAME;
            case "jmbag" -> FieldValueGetters.JMBAG;
            default -> null;
        };

        return iFieldValueGetter;
    }


}
