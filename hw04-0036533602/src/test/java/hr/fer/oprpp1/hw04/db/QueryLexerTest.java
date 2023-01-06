package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QueryLexerTest {

    @Test
    public void testWrongAtributeThrows(){
        QueryLexer lexer = new QueryLexer("Jmbag=\"0000000003\"");
        assertThrows(QueryLexerException.class, lexer::nextToken);
    }
    @Test
    public void testWrongLogicalOperatorThrows(){
        QueryLexer lexer = new QueryLexer(" or");
        assertThrows(QueryLexerException.class, lexer::nextToken);

        QueryLexer lexer1 = new QueryLexer(" anda");
        assertThrows(QueryLexerException.class, lexer1::nextToken);
    }

    @Test
    public void testAfterEOFThrows(){
        QueryLexer lexer = new QueryLexer("jmbag=\"0000000003\"");
        lexer.nextToken();
        while(lexer.getCurrentToken().getTokenType() != TokenType.EOF) lexer.nextToken();

        assertThrows(QueryLexerException.class, lexer::nextToken);
    }

    @Test
    public void testInvalidComparisonOperatorThrows(){
        QueryLexer lexer = new QueryLexer(" jmbag==\"0000000003\"");
        lexer.nextToken();
        assertThrows(QueryLexerException.class, lexer::nextToken);
    }


    @Test
    public void testNotClosingStringLiteralThrows(){
        QueryLexer lexer = new QueryLexer(" jmbag=\"0000000003");

        lexer.nextToken();
        lexer.nextToken();
        assertThrows(QueryLexerException.class, lexer::nextToken);
    }



    @Test
    public void testSimpleQuery(){
        QueryLexer lexer = new QueryLexer(" jmbag=\"0000000003\"");
        QueryLexer lexer1 = new QueryLexer(" lastName = \"Blažić\"");

        TokenType[] expected = new TokenType[]{TokenType.ATTRIBUTE,TokenType.COMPARISON_OP,TokenType.STRING_LITERAL,TokenType.EOF};

        checkResult(expected,lexer);
        checkResult(expected,lexer1);
    }

    @Test
    public void testComplexQuery(){
        QueryLexer lexer = new QueryLexer(" firstName>\"A\" and lastName LIKE \"B*ć\"");

        TokenType[] expected = new TokenType[]{TokenType.ATTRIBUTE,TokenType.COMPARISON_OP, TokenType.STRING_LITERAL
                ,TokenType.LOGICAL_OP,TokenType.ATTRIBUTE,TokenType.COMPARISON_OP, TokenType.STRING_LITERAL,TokenType.EOF};

        checkResult(expected,lexer);
    }

    @Test
    public void testMostComplexQuery(){
        QueryLexer lexer = new QueryLexer(" firstName>\"A\" and firstName<\"C\" and lastName LIKE \"B*ć\" and jmbag>\"0000000002\"");

        TokenType[] expected = new TokenType[]{
                 TokenType.ATTRIBUTE,TokenType.COMPARISON_OP, TokenType.STRING_LITERAL
                ,TokenType.LOGICAL_OP,TokenType.ATTRIBUTE,TokenType.COMPARISON_OP, TokenType.STRING_LITERAL
                ,TokenType.LOGICAL_OP,TokenType.ATTRIBUTE,TokenType.COMPARISON_OP, TokenType.STRING_LITERAL
                ,TokenType.LOGICAL_OP,TokenType.ATTRIBUTE,TokenType.COMPARISON_OP, TokenType.STRING_LITERAL,
                TokenType.EOF};

        checkResult(expected,lexer);
    }

    @Test
    public void testQuery(){
        QueryLexer lexer = new QueryLexer("firstName != \"Dean\"");

        TokenType[] expected = new TokenType[]{
            TokenType.ATTRIBUTE, TokenType.COMPARISON_OP,TokenType.STRING_LITERAL, TokenType.EOF
        };

        checkResult(expected,lexer);
    }

    private void checkResult(TokenType[] expected, QueryLexer lexer){
        lexer.nextToken();
        int i = 0;
        while(lexer.getCurrentToken().getTokenType() != TokenType.EOF){
            if(expected[i] != lexer.getCurrentToken().getTokenType()) fail();
            i++;
            lexer.nextToken();
        }

        if(expected[i] != TokenType.EOF) fail();
    }

}