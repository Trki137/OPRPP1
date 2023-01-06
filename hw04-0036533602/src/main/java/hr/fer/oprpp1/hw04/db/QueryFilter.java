package hr.fer.oprpp1.hw04.db;

import java.util.List;

/**
 * QueryFilter checks if student record satisfies all conditions defined in query
 *
 * @author Dean Trkulja
 * @version 1.0
 */

public class QueryFilter implements IFilter{
    /**
     * List of all conditional expressions used in query
     */
    List<ConditionalExpression> expressions;

    public QueryFilter(List<ConditionalExpression> expressions){
        this.expressions = expressions;
    }

    /**
     * Determines if {@code record} satisfies all conditions
     *
     * @param record student record that checks condition
     * @return boolean true if {@code record} satisfies all conditions, else false
     */
    @Override
    public boolean accepts(StudentRecord record) {
        for(ConditionalExpression expression : expressions){
            String fieldValue = expression.getiFieldValueGetter().get(record);
            String literal = expression.getLiteral();
            if(!expression.getiComparisonOperator().satisfied(fieldValue,literal)) return false;
        }

        return true;
    }
}
