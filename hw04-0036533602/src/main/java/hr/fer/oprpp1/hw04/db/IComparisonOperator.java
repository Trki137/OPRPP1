package hr.fer.oprpp1.hw04.db;

/**
 * Interface represents strategy that is used for comparing 2 String values
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public interface IComparisonOperator {

    /**
     * Determines if 2 string literals are equal or not
     *
     * @param value1 value that is being compared
     * @param value2 value that is the comparison
     * @return true if two string literals are the same
     */
    public boolean satisfied(String value1, String value2);
}
