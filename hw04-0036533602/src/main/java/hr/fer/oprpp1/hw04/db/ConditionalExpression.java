package hr.fer.oprpp1.hw04.db;

/**
 * Represent one conditional expression of query
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public class ConditionalExpression {
    /**
     * Field attribute in database
     */
    private IFieldValueGetter iFieldValueGetter;
    /**
     * Represent what value do we search for
     */
    private String literal;
    /**
     * Comparison operator used in expression
     */
    private IComparisonOperator iComparisonOperator;

    /**
     * Constructor for ConditionalExpression
     *
     * @param iFieldValueGetter Field attribute in database
     * @param literal Represent what value do we search for
     * @param iComparisonOperator Comparison operator used in expression
     */
    public ConditionalExpression(IFieldValueGetter iFieldValueGetter, String literal, IComparisonOperator iComparisonOperator){
        this.iFieldValueGetter = iFieldValueGetter;
        this.literal = literal;
        this.iComparisonOperator = iComparisonOperator;
    }

    /**
     *
     * @return IComparisonOperator
     */

    public IComparisonOperator getiComparisonOperator() {
        return iComparisonOperator;
    }

    /**
     *
     * @return IFieldValueGetter
     */

    public IFieldValueGetter getiFieldValueGetter() {
        return iFieldValueGetter;
    }

    /**
     *
     * @return String
     */

    public String getLiteral() {
        return literal;
    }
}
