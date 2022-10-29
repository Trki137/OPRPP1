package hr.fer.oprpp1.custom.collections;

import java.util.Objects;

/**
 * Dictionary is bad implementation of maps
 *
 * @author Dean Trkulja
 * @version 1.0
 *
 * @param <K> type parameter for key
 * @param <V>type parameter for value
 */
public class Dictionary<K,V> {
    /**
     * Variable in which we store key value pairs.
     * Pair<K,V> is inner class that we use to store key value pairs
     */
    private final ArrayIndexedCollection<Pair<K,V>> dictionary;

    /**
     * Default constructor
     */
    public Dictionary(){
        dictionary = new ArrayIndexedCollection<>();
    }

    /**
     * Checks if dictionary is empty
     * @return boolean true if dictionary is empty or else false
     */
    public boolean isEmpty(){
        return dictionary.isEmpty();
    }

    /**
     * Returns number of elements stored in dictionary
     *
     * @return int number of elements stored in dictionary
     */
    public int size() {return dictionary.size();}

    /**
     * All elements from dictionary are erased
     */
    public void clear(){
        dictionary.clear();
    }

    /**
     *
     * Adds new pair into the dictionary if there is no pair with that key,
     * if there is then overwrites value
     *
     * @param key represents key of the pair record
     * @param value represents value of the pair record
     * @return V null if Pair in dictionary is not overwritten, in other case
     * it returns value that was previously stored
     */
    public V put(K key, V value){
        Pair<K,V> pair = getPairWithKey(key);

        if(pair != null) {
            V oldValue = pair.getValue();
            pair.setValue(value);
            return oldValue;
        }
        else
            dictionary.add(new Pair<>(key, value));

        return null;
    }

    /**
     * Returns value that is in pair with key
     *
     * @param key key of pair
     * @return V null if key doesn't exists else it returns value of pair
     * @throws NullPointerException if key is null
     */
    public V get(K key){
        if(key == null) throw new NullPointerException();

        Pair<K,V> pair = getPairWithKey(key);

        return pair != null ? pair.getValue() : null;
    }

    /**
     * Removes pair from dictionary that has {@code key} as value
     * @param key key of pair element that we want to remove
     * @return V value of erased element or null if that element doesn't exists
     */
    public V remove(K key){
        if(key == null) throw new NullPointerException();

        V value = get(key);

        if(value != null) dictionary.remove(new Pair<>(key, value));

        return value;
    }

    /**
     * Gets pair that has key if that pair doesn't exist then returns null
     *
     * @param key that we search for
     * @return Pair<K,V> that has key if that pair doesn't exist then returns null
     */

    private Pair<K,V> getPairWithKey(K key){
        for(int i = 0;  i < size(); i++)
            if(dictionary.get(i).getKey().equals(key)) return dictionary.get(i);

        return null;
    }

    /**
     * All elements in dictionary
     *
     * @return String all elements in dictionary
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < dictionary.size(); i++)
            sb.append(dictionary.get(i).key).append(" -> ").append(dictionary.get(i).value).append("\n");

        return sb.toString();
    }

    /**
     * Class that we use to encapsulate key value pairs
     * @param <K> type parameter for key
     * @param <V> type parameter for value
     */

    private static class Pair<K,V>{
        /**
         * Key of pair
         */
        private final K key;
        /**
         * Value of pair
         */
        private V value;

        /**
         * Constructor for Pair<K,V>
         * @param key key of pair
         * @param value value of pair, it can be null
         * @throws NullPointerException if key is null
         */

        public Pair(K key, V value){
            if(key == null) throw new NullPointerException("Key can't be null");

            this.key = key;
            this.value = value;
        }

        /**
         * Getter for key
         * @return K key
         */
        public K getKey() {
            return key;
        }

        /**
         * Getter for value
         * @return V value
         */
        public V getValue() {
            return value;
        }

        /**
         * Setter for value
         * @param value new value of pair
         */
        public void setValue(V value) {
            this.value = value;
        }

        /**
         * String representation of pair
         * @return String representation of pair
         */
        @Override
        public String toString() {
            return key +" -> "+value;
        }

        /**
         * Checks equality between Pair<K,V> and other object
         *
         * @param obj instance to compare current pair
         * @return boolean if two instance of Pair<K,V> are equal
         */
        @Override
        public boolean equals(Object obj) {
            if(obj == null) return false;
            if(!(obj instanceof Pair)) return false;

            Pair<?,?> other =(Pair<?, ?>) obj;

            return this.key.equals(other.key) && this.value.equals(other.value);

        }

        /**
         * Hashcode of object
         * @return int hashcode of object
         */
        @Override
        public int hashCode() {
            return Objects.hash(key,value);
        }
    }
}
