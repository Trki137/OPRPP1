package hr.fer.oprpp1.hw04.db;

/**
 * Defines what field from table is used ,{@link IFieldValueGetter} implementations, that are eligible in our query
 *
 * @author Dean Trkulja
 * @version 1.0
 */

public class FieldValueGetters {

    /**
     * Returns first name of StudentRecord instance
     */

    public static IFieldValueGetter FIRST_NAME = StudentRecord::getFirstName;

    /**
     * Returns last name of StudentRecord instance
     */

    public static IFieldValueGetter LAST_NAME = StudentRecord::getLastName;

    /**
     * Returns jmbag of StudentRecord instance
     */

    public static IFieldValueGetter JMBAG = StudentRecord::getJmbag;
}
