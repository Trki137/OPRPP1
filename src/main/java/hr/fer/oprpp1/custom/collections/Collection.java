package hr.fer.oprpp1.custom.collections;

import java.nio.charset.UnsupportedCharsetException;

/**
 * Base class for our implementation of ArrayIndexedCollection and LinkedList
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public class Collection {
    /**
     * Default constructor
     */
    protected Collection(){

    }

    /**
     * Checks if collection is empty or not
     *
     * @return true if it is not empty else false
     */

    public boolean isEmpty(){
        return this.size() > 0;
    }

    /**
     * Checks how many elements are there in collection
     *
     * @return number of elements in collection
     */

    public int size(){
        return 0;
    }

    /**
     * Adds {@param value} to collection
     *
     * @param value value that we want to store in collection
     */

    public void add(Object value){

    }

    /**
     * Checks if element exists
     *
     * @param value element that we are looking for
     * @return true if exists else false
     */
    public boolean contains(Object value){
        return false;
    }

    /**
     * Removes element with value of {@param value}
     *
     * @param value value that we want to remove from collection
     * @return true if it is removed else false
     */

    public boolean remove(Object value){
        return false;
    }

    /**
     * Turns collection to object array, but collection is not affected
     *
     * @return Object array
     */
    public Object[] toArray(){
        throw new UnsupportedCharsetException("");
    }

    /**
     * Every element of collection is sent to processor.process()
     *
     * @param processor instance of class Processor
     */

    public void forEach(Processor processor){

    }

    /**
     * Adds all element from collection {@param other} to our collection
     * @param other
     */
    public void addAll(Collection other){

        if(other == null)
            throw new NullPointerException();
        /**
         * Local class that extends class Processor and overrides method process
         */
        class AddCollectionProcessor extends Processor{
            @Override
            /**
             * We send value and it's going to be stored in collection
             *
             * @param value value that we want to store
             */
            public void process(Object value){
                add(value);
            }
        }

        other.forEach(new AddCollectionProcessor());

    }

    /**
     * Deletes all elements from collection and sets size to 0
     */
    public void clear(){

    }

}
