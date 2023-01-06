package hr.fer.oprpp1.hw04.db;

/**
 * Interface represents strategy that is used for getting value from StudentRecord instance
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public interface IFieldValueGetter {
    /**
     * Returns value of specific field value of {@code record}
     *
     * @param record the student record from which we request specific field value
     * @return String value of specific field value
     */
    public String get(StudentRecord record);
}
