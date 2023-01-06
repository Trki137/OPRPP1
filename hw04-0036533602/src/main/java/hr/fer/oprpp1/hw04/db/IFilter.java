package hr.fer.oprpp1.hw04.db;

/**
 * Interface represent strategy for filtering student records
 *
 * @author Dean Trkulja
 * @version 1.0
 */

public interface IFilter {
    /**
     * Determines if {@code record} satisfies condition
     *
     * @param record student record that checks condition
     * @return boolean true if record satisfies condition, else false
     */
    boolean accepts(StudentRecord record);
}
