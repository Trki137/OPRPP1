package hr.fer.oprpp1.custom.collections;


import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 *
 *
 * @author Dean Trkulja
 * @version 1.0
 *
 *
 */

public class LinkedListIndexedCollection<T> implements List<T> {

    /**
     * number of elements in collection
     */
    private int size;
    /**
     * reference to the first node in list
     */
    private ListNode<T> firstNode;
    /**
     *  reference to the last node in list
     */
    private ListNode<T> lastNode;

    long momodificationCount = 0;

    /**
     * Default construct where we set size to 0 and {@code firstNode} and {@code lastNode} to null
     */

    public LinkedListIndexedCollection(){
        this.size = 0;
        this.firstNode = this.lastNode = null;
    }

    /**
     * Copies elements from {@param collection} to our new collection
     *
     * @param collection elements of this collection are copied into our new collection
     */
    public LinkedListIndexedCollection(Collection<T> collection){
        addAll(collection);
    }

    /**
     * Returns value of collection in position {@param index}
     * Throws IndexOutOfBoundsException when index is smaller then 0 or greater then size
     *
     * @param index specifies index of element that we want to get
     * @return element which is located at position {@param index}
     *
     */
    @Override

    public T get(int index){
        if(index < 0 || index > this.size - 1) throw  new IndexOutOfBoundsException();

        ListNode<T> node = firstNode;
        int i = 0;

        while (node != null){

            if(i == index) return node.value;
            node = node.nextNode;
            i++;
        }

        return null;
    }

    /**
     *
     * Inserts value in specific position
     *
     * Throws 2 exceptions:
     *    - IndexOutOfBoundsException is thrown when position is greater then size of collection or smaller then 0
     *    - NullPointerException is thrown when null value occurs in {@param value}
     *
     * @param value value that we store in our collection
     * @param position in which position do we want to store element, eligible value is between 0 and current size of collection
     *
     */
    @Override
    public void insert(T value, int position){
        if(position < 0 || position > size) throw new IllegalArgumentException();
        if(value == null) throw new NullPointerException();

        ListNode<T> insertedNode = new ListNode<>(null,null,value);

        if(position == size) add(value);
        else if(position == 0){

            firstNode.prevNode = insertedNode;
            insertedNode.nextNode = firstNode;
            firstNode = insertedNode;
            size++;
            momodificationCount++;

        } else {
            ListNode<T> foundNode = getNode(position);
            foundNode.prevNode.nextNode = insertedNode;
            insertedNode.prevNode = foundNode.prevNode;
            foundNode.prevNode = insertedNode;
            insertedNode.nextNode = foundNode;
            size++;
            momodificationCount++;
        }
    }



    /**
     * Returns index of element that we are searching
     *
     * @param value element that we are searching for
     * @return -1 if element doesn't exists or value is null
     *          Index of element if elements exists
     */
    @Override
    public int indexOf(Object value){
        if(value == null) return -1;

        int foundPosition = -1;
        int i = 0;
        ListNode<T> node = firstNode;

        while (node != null && foundPosition == -1){
            if(node.value.equals(value)) foundPosition = i;
            node = node.nextNode;
            i++;
        }

        return foundPosition;
    }
    /**
     * Removes element in position {@param index}
     * Throws IllegalArgumentException when index is greater then size - 1 or smaller then 0
     *
     * @param index element which is located at position {@param index} is deleted
     */

    @Override
    public void remove(int index){
        if(index < 0 || index > size - 1) throw new IllegalArgumentException();

        if(index == 0) {
            firstNode = firstNode.nextNode;
            firstNode.prevNode = null;
        }else if(index == size - 1){
            lastNode = lastNode.prevNode;
            lastNode.nextNode = null;
        }else {
            ListNode<T> node = firstNode;
            int i = 0;

            while(node != null){
                if(i == index){
                    node.prevNode.nextNode = node.nextNode;
                    node.nextNode.prevNode = node.prevNode;

                    node.prevNode = node.nextNode = null;
                }
                node = node.nextNode;
                i++;
            }
        }
        momodificationCount++;
        size--;

        }

    /**
     * @return size of collection
     */
    @Override
    public int size(){
        return this.size;
    }

    /**
     * Adds new element to collection, newly added element is lastNode in list
     *
     * Throws NullPointerException if {@param value} is null
     *
     * @param value value that we want to store in collection
     */
    @Override
    public void add(T value){
        if(value == null) throw new NullPointerException();

        ListNode<T> newNode = new ListNode<>(lastNode,null,value);

        if(firstNode == null)
            firstNode = newNode;
        else
            this.lastNode.nextNode = newNode;

        this.lastNode = newNode;
        momodificationCount++;
        size++;
    }

    /**
     * Checks if element exists in collection
     *
     * @param value element that we are searching for in our collection
     * @return true if exists else false
     *
     */
    @Override
    public boolean contains(Object value){
        ListNode<T> node = firstNode;

        while(node != null){
            if(node.value.equals(value)) return true;
            node = node.nextNode;
        }

        return false;
    }

    /**
     * Removes first element in collection which has value equal to {@param value}
     *
     * @param value element that we want to remove
     * @return true if element is removed in other cases return false
     */

    @Override
    public boolean remove(Object value){

        int index = indexOf(value);

        if(index == -1) return false;

        remove(index);
        return true;
    }

    /**
     * Copies our collection to array
     * @return array of objects
     */

    @Override
    public Object[] toArray(){

        ListNode<T> node = this.firstNode;
        Object[] arr = new Object[this.size];
        int i = 0;

        while(node != null){
            arr[i] = node.value;
            node = node.nextNode;
            i++;
        }

        return arr;
    }

    /**
     * For each value in collection we call proccesor.process
     * @param processor instance of class Processor
     */
    @Override
    public void forEach(Processor<T> processor){
        ListNode<T> node = firstNode;

        while(node != null){
            processor.process(node.value);
            node = node.nextNode;
        }
    }

    /**
     * Clears list by setting {@code firstNode} and {@code lastNode} to null, java garbage collector takes care rest
     * Sets size to 0
     */
    @Override
    public void clear(){
        firstNode = lastNode = null;
        size = 0;
        momodificationCount++;
    }


    /**
     * Searches node in O(n/2 +1)
     *
     * @param position int param which tells us which position we need
     * @return node that will use for inserting new node
     */

    private ListNode<T> getNode(int position){
        ListNode<T> node;
        if(position >= 0 && position < size/2) {
            int i = 0;
            node = firstNode;

            while (node != null) {

                if (position == i) return node;
                i++;
                node = node.nextNode;

            }
        }else {
            int i = size - 1;
            node = lastNode;

            while (node != null) {

                if (position == i) return node;
                i--;
                node = node.prevNode;

            }
        }
        return null;
    }

    /**
     *  To create an object that will get us all elements of LinkedListCollection
     *
     * @return ElementsGetter creates and returns object that has implemented ElementsGetter interface
     */

    @Override
    public ElementsGetter<T> createElementsGetter(){
        return new ElementsGetterImpl<>(this);
    }



    /**
     * Private class that implements interface ElementsGetter.
     * It will be used to get elements in order from firstNode to lastNode
     */


    private static class ElementsGetterImpl<T> implements ElementsGetter<T>{

        /**
         * Reference to the list that we are using
         */
        private final LinkedListIndexedCollection<T> list;

        /**
         * Represents current node that we are on
         */
        private ListNode<T> node;

        /**
         * Variable that tracks if list has been modified since creating this object
         */
        private final long saveModificationCount;

        /**
         * Constructor for ElemntsGetterImpl
         *
         * @param list reference to current LinkedList element
         */

        ElementsGetterImpl(LinkedListIndexedCollection<T> list){
            this.list = list;
            this.node = list.firstNode;
            this.saveModificationCount = list.momodificationCount;
        }

        /**
         * Checks if there are more elements available
         *
         * @return boolean if there is more elements returns true
         */
        @Override
        public boolean hasNextElement() {
            if(saveModificationCount != list.momodificationCount) throw new ConcurrentModificationException();
            return node != null;
        }

        /**
         * Returns next element
         *
         * @return Object returns next element
         */
        @Override
        public T getNextElement() {
            if(!hasNextElement())
                throw new NoSuchElementException("There are no more elements");

            T value = node.value;
            node = node.nextNode;
            return value;
        }

    }




    /**
     * Class that help's in implementing linked list and it represents one node in list
     */

    private static class ListNode<T>{
        /**
         * references to the previous node if it exists
         */
        private ListNode<T> prevNode;
        /**
         * references to the next node if it exists
         */
        private ListNode<T> nextNode;
        /**
         * Value of the node
         */
        private final T value;

        /**
         *
         * @param prevNode reference to the previous node, it can be null
         * @param nextNode reference to the next node, it can be null
         * @param value value of the node
         *
         */

        public ListNode(ListNode<T> prevNode, ListNode<T> nextNode, T value){
            this.prevNode = prevNode;
            this.nextNode = nextNode;
            this.value = value;
        }

    }

}
