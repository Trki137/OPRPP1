package hr.fer.oprpp1.custom.collections;

/**
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public interface ElementsGetter<T> {
    /**
     * Checks if there are more elements available
     *
     * @return boolean if there is more elements retuns true
     */
    boolean hasNextElement();

    /**
     * Returns next element
     *
     * @return Object returns next element
     */
    T getNextElement();

    /**
     * All remaining elements are processed by an instance that has implemented interface Processor
     *
     * @param p instance of Processor class
     */
    default void processRemaining(Processor<T> p) {
        while (hasNextElement()){
            p.process(getNextElement());
        }
    }
}
