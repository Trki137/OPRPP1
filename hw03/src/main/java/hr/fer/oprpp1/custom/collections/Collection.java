package hr.fer.oprpp1.custom.collections;

/**
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public interface Collection<T> {

    /**
     * Checks if collection is empty or not
     *
     * @return true if it is not empty else false
     */

    default boolean isEmpty(){
        return !(this.size() > 0);
    }

    /**
     * Checks how many elements are there in collection
     *
     * @return number of elements in collection
     */

    int size();

    /**
     * Adds {@param value} to collection
     *
     * @param value value that we want to store in collection
     */

    void add(T value);

    /**
     * Checks if element exists
     *
     * @param value element that we are looking for
     * @return true if exists else false
     */
    boolean contains(Object value);

    /**
     * Removes element with value of {@param value}
     *
     * @param value value that we want to remove from collection
     * @return true if it is removed else false
     */

    boolean remove(Object value);

    /**
     * Turns collection to object array, but collection is not affected
     *
     * @return Object array
     */
    Object[] toArray();

    /**
     * Every element of collection is sent to processor.process()
     *
     * @param processor instance of class Processor
     */

    default void forEach(Processor<T> processor){
        ElementsGetter<T> getter = createElementsGetter();
        getter.processRemaining(processor);
    }

    /**
     * Adds all element from collection {@param other} to our collection
     */
    default void addAll(Collection<T> other){

        if(other == null)
            throw new NullPointerException();
        class AddCollectionProcessor implements Processor<T>{
            @Override
            public void process(T value){
                add(value);
            }
        }

        other.forEach(new AddCollectionProcessor());

    }

    /**
     * Deletes all elements from collection and sets size to 0
     */
    void clear();

    /**
     * To create an object that will get us all elements of Collection
     *
     * @return ElementsGetter creates and returns object that has implemented ElementsGetter interface
     */

    ElementsGetter<T> createElementsGetter();

    /**
     *
     * Method that adds in Collection if condition is satisfied
     *
     * @param col copy from this collection elements
     * @param tester object that has implemented interface Tester
     */

       default void addAllSatisfying(Collection<T> col, Tester<T> tester) {
        ElementsGetter<T> getter = col.createElementsGetter();
        getter.processRemaining( value-> {
            if(tester.test(value)) add(value);
        });
    }
}
