package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QueryParserTest {

    @Test
    public void testQueryNullThrows(){
        assertThrows(NullPointerException.class, () -> new QueryParser(null));
    }

    @Test
    public void testDirectQuery(){
        QueryParser parser = new QueryParser("jmbag = \"0000000001\"");
        assertTrue(parser.isDirectQuery());

        QueryParser parser1 = new QueryParser("jmbag > \"0000000001\"");
        assertFalse(parser1.isDirectQuery());
    }

    @Test
    public void testGetQueriedJMBAG(){
        QueryParser parser = new QueryParser("jmbag = \"0000000001\"");
        assertEquals("0000000001",parser.getQueriedJMBAG());
    }

    @Test
    public void testGetQueriedJMBAGNotDirectQueryThrows(){
        QueryParser parser1 = new QueryParser("jmbag > \"0000000001\"");
        assertThrows(QueryParserException.class,() ->parser1.getQueriedJMBAG());
    }

    @Test
    public void testGetQuery(){
        QueryParser parser = new QueryParser("jmbag = \"0000000001\"");
        QueryParser parser1 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");

        assertEquals(1,parser.getQuery().size());
        assertEquals(2,parser1.getQuery().size());
    }

}
