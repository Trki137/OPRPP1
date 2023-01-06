package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ComparisonOperatorsTest {

    @Test
    public void testLike(){
        IComparisonOperator like = ComparisonOperators.LIKE;

        assertTrue(like.satisfied("",""));
        assertTrue(like.satisfied("a","a"));
        assertTrue(like.satisfied("aba!","aba!"));
        assertTrue(like.satisfied("AasDa.?23","AasDa.?23"));

        assertFalse(like.satisfied("a","b"));
        assertFalse(like.satisfied("A","a"));

        assertFalse(like.satisfied("abc","ac*"));
        assertFalse(like.satisfied("aaa", "ab*"));
        assertFalse(like.satisfied("aa","ab*"));
        assertFalse(like.satisfied("aa", "*b"));
        assertFalse(like.satisfied("ba","a*"));

        assertTrue(like.satisfied("Ovo je b","*b"));
        assertTrue(like.satisfied("a pocinje zavrsava b","a*b"));
        assertTrue(like.satisfied("zavrsava b","*b"));
        assertFalse(like.satisfied("AAA","AA*AA"));
        assertTrue(like.satisfied("AAAA","AA*AA"));
        assertTrue(like.satisfied("aa","aa*"));
        assertTrue(like.satisfied("Trkulja", "Trk*"));

        assertTrue(like.satisfied("Diana", "D*a"));

    }

    @Test
    public void testLess(){
        IComparisonOperator less = ComparisonOperators.LESS;

        assertTrue(less.satisfied("a", "b"));
        assertTrue(less.satisfied("Ana", "Jasna"));

        assertFalse(less.satisfied("b","a"));
        assertFalse(less.satisfied("Jasna", "Ana"));
    }

    @Test
    public void testLessOrEquals(){
        IComparisonOperator less = ComparisonOperators.LESS_OR_EQUALS;

        assertTrue(less.satisfied("a", "a"));
        assertTrue(less.satisfied("a", "b"));
        assertTrue(less.satisfied("Ana", "Jasna"));

        assertFalse(less.satisfied("b","a"));
        assertFalse(less.satisfied("Jasna", "Ana"));
    }

    @Test
    public void testGreater(){
        IComparisonOperator less = ComparisonOperators.GREATER;

        assertFalse(less.satisfied("a", "b"));
        assertFalse(less.satisfied("Ana", "Jasna"));

        assertTrue(less.satisfied("b","a"));
        assertTrue(less.satisfied("Jasna", "Ana"));
    }

    @Test
    public void testGreaterOrEquals(){
        IComparisonOperator less = ComparisonOperators.GREATER_OR_EQUALS;

        assertFalse(less.satisfied("a", "b"));
        assertFalse(less.satisfied("Ana", "Jasna"));

        assertTrue(less.satisfied("a", "a"));
        assertTrue(less.satisfied("b","a"));
        assertTrue(less.satisfied("Jasna", "Ana"));
    }

    @Test
    public void testEquals(){
        IComparisonOperator less = ComparisonOperators.EQUALS;

        assertFalse(less.satisfied("a", "b"));
        assertFalse(less.satisfied("Ana", "Jasna"));

        assertTrue(less.satisfied("a", "a"));
        assertTrue(less.satisfied("1","1"));
        assertTrue(less.satisfied("Jasna", "Jasna"));
    }

    @Test
    public void testNotEquals(){
        IComparisonOperator less = ComparisonOperators.NOT_EQUALS;

        assertTrue(less.satisfied("a", "b"));
        assertTrue(less.satisfied("Ana", "Jasna"));

        assertFalse(less.satisfied("a", "a"));
        assertFalse(less.satisfied("1","1"));
        assertFalse(less.satisfied("Jasna", "Jasna"));
    }
}
