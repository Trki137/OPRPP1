package hr.fer.oprpp1.hw04.db;

/**
 * Defines comparison operators ,{@link IComparisonOperator} implementations, that are eligible in our query
 *
 * @author Dean Trkulja
 * @version 1.0
 */

public class ComparisonOperators {

    /**
     * Checks if {@code value1} is lexicographically less than {@code value2}
     */

    public static final IComparisonOperator LESS = ((value1, value2) -> value1.compareTo(value2) < 0);

    /**
     * Checks if {@code value1} is lexicographically less or equal than {@code value2}
     */

    public static final IComparisonOperator LESS_OR_EQUALS = ((value1, value2) -> value1.compareTo(value2) <= 0);

    /**
     * Checks if {@code value1} is lexicographically greater than {@code value2}
     */
    public static final IComparisonOperator GREATER = ((value1, value2) -> value1.compareTo(value2) > 0);

    /**
     * Checks if {@code value1} is lexicographically greater or equal than {@code value2}
     */
    public static final IComparisonOperator GREATER_OR_EQUALS = ((value1, value2) -> value1.compareTo(value2) >= 0);

    /**
     * Checks if {@code value1} is lexicographically equal than {@code value2}
     */
    public static final IComparisonOperator EQUALS = ((value1, value2) -> value1.compareTo(value2) == 0);

    /**
     * Checks if {@code value1} is lexicographically not equal than {@code value2}
     */
    public static final IComparisonOperator NOT_EQUALS = ((value1, value2) -> value1.compareTo(value2) != 0);

    /**
     * Checks if {@code value1} follows patter of {@code value2}
     */
    public static final IComparisonOperator LIKE = ((value1, value2) -> {

        char[] checkedCharacters = value1.toCharArray();
        char[] patternCharacters = value2.toCharArray();

        int checkedCharactersCounter = 0;
        int patternCharactersCounter = 0;

        while(checkedCharactersCounter < checkedCharacters.length &&
                patternCharactersCounter < patternCharacters.length){

            if(patternCharacters[patternCharactersCounter] == '*'){

                if(patternCharactersCounter + 1 == patternCharacters.length) return true;
                    patternCharactersCounter++;
                    String endsWith = String.copyValueOf(patternCharacters,patternCharactersCounter,patternCharacters.length - patternCharactersCounter);
                    String notCheckedYet = String.copyValueOf(checkedCharacters,checkedCharactersCounter, checkedCharacters.length - checkedCharactersCounter);
                    return notCheckedYet.endsWith(endsWith);

            }


            if(checkedCharacters[checkedCharactersCounter] != patternCharacters[patternCharactersCounter])
                return false;

            patternCharactersCounter++;
            checkedCharactersCounter++;


        }

        if(patternCharactersCounter + 1 == patternCharacters.length)
            if(patternCharacters[patternCharactersCounter] == '*') return true;

        return checkedCharactersCounter == checkedCharacters.length && patternCharactersCounter == patternCharacters.length;
    });
}
