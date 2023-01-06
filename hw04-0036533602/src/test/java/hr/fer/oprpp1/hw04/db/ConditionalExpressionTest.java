package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ConditionalExpressionTest {

    @Test
    public void testConditionSatisfied(){
        StudentRecord studentRecord = new StudentRecord(
                "0036533312",
                "Dean",
                "Trkulja",
                4
        );

        ConditionalExpression expr = new ConditionalExpression(
                FieldValueGetters.LAST_NAME,
                "Trk*",
                ComparisonOperators.LIKE
                );

        boolean recordSatisfies = expr.getiComparisonOperator().satisfied(
                expr.getiFieldValueGetter().get(studentRecord),
                expr.getLiteral()
        );

        assertTrue(recordSatisfies);
    }

    @Test
    public void testConditionNotSatisfied(){
        StudentRecord studentRecord = new StudentRecord(
                "0036533312",
                "Dean",
                "Trkulja",
                4
        );

        ConditionalExpression expr = new ConditionalExpression(
                FieldValueGetters.LAST_NAME,
                "trk*",
                ComparisonOperators.LIKE
        );

        boolean recordSatisfies = expr.getiComparisonOperator().satisfied(
                expr.getiFieldValueGetter().get(studentRecord),
                expr.getLiteral()
        );

        assertFalse(recordSatisfies);
    }

}
