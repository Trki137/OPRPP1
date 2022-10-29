package hr.fer.oprpp1.custom.collections;

/**
 *
 * @author Dean Trkulja
 * @version 1.0
 *
 * @param <T> type parameter for List
 */
public interface List<T> extends Collection<T> {

    /**
     * Returns value at given index
     *
     * @param index index of value that we want
     * @return T value
     */
    T get(int index);

    /**
     * Inserts value at given position
     * @param value that we want to store
     * @param position in which we want to store
     */
    void insert(T value, int position);

    /**
     * Returns index of value
     * @param value that we are searching for
     * @return index of value
     */
    int indexOf(Object value);

    /**
     * Remove element at given index
     * @param index position to remove
     */
    void remove(int index);

}
