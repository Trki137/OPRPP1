package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Map implementation
 *
 * @author Dean Trkulja
 * @version 1.0
 *
 * @param <K> type parameter for key
 * @param <V> type parameter for value
 */
public class SimpleHashtable<K,V> implements Iterable<SimpleHashtable.TableEntry<K,V>> {
    /**
     * Table for table entries
     */
    private TableEntry<K,V>[] table;
    /**
     * Number of elements in table
     */
    private int size;
    /**
     * How many times did we removed or added new table entry
     */

    private int modificationCount;

    /**
     * Default size of table
     */

    private final static int DEFAULT_SIZE = 16;


    /**
     * Default constructor
     */
    public SimpleHashtable(){
        this(DEFAULT_SIZE);
    }


    /**
     * Constructor with size parameter
     * @param size size of table
     */
    @SuppressWarnings("unchecked")
    public SimpleHashtable(int size){
        if(size < 1) throw new IllegalArgumentException();
        size = getNewSize(size);

        table = (TableEntry<K, V>[]) new TableEntry[size];
        modificationCount = 0;
    }

    /**
     * If key exists in table then overrides value, else inserts new table entry
     * @param key key for table entry
     * @param value value for table entry
     * @return V old value of table entry if key already existed else null
     * @throws NullPointerException if key is null
     */
    public V put(K key, V value){
        if(key == null) throw new NullPointerException();

        if(((double)size / (double) table.length ) >= 0.75) increaseCapacity();

        int index = Math.abs(key.hashCode() % table.length);

        if(table[index] == null)
            table[index] = new TableEntry<>(key,value);
        else {
            TableEntry<K,V> helper = table[index];

            while(helper != null){

                if(helper.getKey().equals(key)){
                    V oldValue = helper.value;
                    helper.setValue(value);
                    return oldValue;
                }

                if(helper.next == null) {
                    helper.next = new TableEntry<>(key,value);
                    break;
                }

                helper = helper.next;
            }

        }

        this.size++;
        this.modificationCount++;
        return null;
    }

    /**
     * Returns value of table entry with {@code key}
     *
     * @param key key of value that we are searching for
     * @return V if key doesn't exists returns null else returns value of that table entry
     */


    public V get(Object key){
        if(key == null) return null;

        int index = Math.abs(key.hashCode() % table.length);

        TableEntry<K,V> entry = table[index];

            while(entry != null){
                if(entry.getKey().equals(key))
                    return entry.value;

                entry = entry.next;
            }

        return null;
    }

    /**
     * Size of table
     * @return int Size of table
     */

    public int size(){
        return this.size;
    }

    /**
     * Removes all elements from table
     */

    public void clear(){
        Arrays.fill(table,null);
        size = 0;
    }

    /**
     * Checks if table contains entry with key {@code key}
     * @param key key that we are searching for
     * @return if key exists true, else false
     */
    public boolean containsKey(Object key){
        return get(key) != null;
    }


    /**
     * Checks if table contains entry with value {@code value}
     * @param value value that we are searching for
     * @return if value exists true, else false
     */

    public boolean containsValue(Object value){

        for (TableEntry<K, V> kvTableEntry : table) {

            TableEntry<K, V> helper = kvTableEntry;

            while (helper != null) {

                if (value == null && helper.value == null) return true;

                if (value != null && value.equals(helper.value)) return true;

                helper = helper.next;

            }
        }

        return false;
    }

    /**
     * Removes element with key {@code key}
     * @param key table entry with that key value
     * @return if key exists returns it's value
     */
    public V remove(Object key){
        if(key == null) return null;

        int index = Math.abs(key.hashCode()%table.length);

        if(table[index] == null) return null;

        V oldValue = null;

        if(table[index].getKey().equals(key)){

            oldValue = table[index].getValue();
            table[index] = table[index].next;

        }
        else{

            TableEntry<K,V> prevNode = table[index];
            TableEntry<K,V> node = table[index].next;
            while(node != null){
                if(node.getKey().equals(key)){
                    prevNode.next = node.next;
                    oldValue = node.value;
                    break;
                }
                prevNode = node;
                node = node.next;
            }
        }
        this.modificationCount++;
        this.size--;
        return oldValue;


    }

    /**
     * All elements from map are stored in array
     *
     * @return TableEntry<K,V>[]  all elements from table
     */

    @SuppressWarnings("unchecked")
    public TableEntry<K,V>[] toArray(){
        TableEntry<K,V>[] arr = (TableEntry<K, V>[]) new TableEntry[size];
        int index = 0;
        for (TableEntry<K, V> kvTableEntry : table) {
            TableEntry<K, V> helper = kvTableEntry;

            while (helper != null) {
                arr[index++] = helper;
                helper = helper.next;
            }
        }
        return arr;
    }

    /**
     * String representation of table
     * @return String representation of table
     */

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (TableEntry<K, V> kvTableEntry : table) {
            TableEntry<K, V> helper = kvTableEntry;
            while (helper != null) {
                sb.append(helper.key).append("=").append(helper.value).append(" , ");
                helper = helper.next;
            }
        }
        return size > 0 ?
                sb.replace(sb.length()-3,sb.length(),"").append("]").toString()
                :sb.append("]").toString();
    }


    /**
     * Finds first size that is power of 2
     * @param currentSize size entered from user
     * @return int size that is power of 2
     */

    private int getNewSize(int currentSize){

        int size = 1;
        while(currentSize > size) size *= 2;
        return size;
    }

    /**
     * When table occupancy level is 75% we double on table capacity
     */
    @SuppressWarnings("unchecked")
    private void increaseCapacity() {
        this.size = 0;

        TableEntry<K,V>[] newTable = (TableEntry<K, V>[]) new TableEntry[table.length*2];
        TableEntry<K,V>[] oldTable = this.table;
        this.table = newTable;

        for (TableEntry<K, V> kvTableEntry : oldTable) {
            TableEntry<K, V> helper = kvTableEntry;

            while (helper != null) {
                put(helper.key, helper.value);
                helper = helper.next;
            }
        }
        this.modificationCount++;

    }

    /**
     * Creates an iterator
     *
     * @return Iterator<TableEntry<K, V>>
     */

    @Override
    public Iterator<TableEntry<K, V>> iterator() {
        return new SimpleHashTableIterator();
    }

    /**
     * Iterator for our SimpleHashtable
     */
    private class SimpleHashTableIterator implements Iterator<SimpleHashtable.TableEntry<K,V>>{

        /**
         * Current index of iterator
         */
        private int currentIndex;

        /**
         * Last table entry that we fetched
         */
        private TableEntry<K,V> lastTableEntry;

        /**
         * is current {@code lastTableEntry} removed
         */
        private boolean isRemoved = false;

        /**
         * For controlling if SimpleHashtable is changed in inappropriate way
         */

        private int currentModificationCount;

        /**
         * Size of table when iterator is initialized
         */
        private final int sizeOfTable;
        /**
         * How many elements did the iterator send
         */
        private int collected;


        /**
         * Constructor
         */
        public SimpleHashTableIterator(){
            this.currentIndex = 0;
            this.lastTableEntry = table[currentIndex];
            this.currentModificationCount = modificationCount;
            this.sizeOfTable = SimpleHashtable.this.size;
            this.collected = 0;
        }

        /**
         * Checks if there is more elements in table
         * @return boolean if there is more elements in table or else false
         */
        public boolean hasNext(){
            return collected < sizeOfTable;
        }

        /**
         * Returns the next element in the iteration
         *
         * @return next element form table
         * @throws ConcurrentModificationException while iterating structure of table has changed
         * @throws NoSuchElementException if there is no more elements to get
         */
        @Override
        public TableEntry<K, V> next() {
            if(modificationCount != currentModificationCount) throw new ConcurrentModificationException();
            if(!hasNext()) throw new NoSuchElementException();

            if(lastTableEntry != null) lastTableEntry = lastTableEntry.next;

            while (lastTableEntry == null){
                lastTableEntry = table[++currentIndex];
            }

            this.isRemoved = false;
            this.collected++;

            return lastTableEntry;
        }

        /**
         * Removes from the underlying collection the last element returned by this iterator
         */
        public void remove(){
            if(this.isRemoved)throw new IllegalStateException();

            SimpleHashtable.this.remove(lastTableEntry.key);

            this.currentModificationCount++;
            this.isRemoved = true;
        }


    }


    /**
     * The {@code TableEntry} class represents a single key-value pair within the hashtable.
     *
     * @author Dean Trkulja
     * @version 1.0
     * @param <K> type parameter of the key object in the table entry.
     * @param <V> type parameter of the value object in the table entry.
     *
     */
    public static class TableEntry<K,V>{
        /**
         * Key of entry
         */
        private final K key;
        /**
         * Value of entry
         */
        private V value;
        /**
         * Reference to next {@code TableEntry}
         */
        private TableEntry<K,V> next;

        /**
         *  Constructor for TableEntry
         *
         * @param key Key of entry
         * @param value Value of entry
         * @throws NullPointerException if key is null
         */

        public TableEntry(K key, V value){
            if(key == null) throw new NullPointerException();

            this.key = key;
            this.value = value;
            next = null;
        }

        /**
         * Getter for value
         * @return V value of entry
         */

        public V getValue() {
            return value;
        }

        /**
         * Getter for key
         * @return K key of entry
         */
        public K getKey() {
            return key;
        }

        /**
         * Setter for value
         * @param value new value stored in entry
         */
        public void setValue(V value) {
            this.value = value;
        }

        /**
         * String representation of entry
         * @return String representation of entry
         */
        @Override
        public String toString() {
            return key + " -> " + value;
        }
    }
}
