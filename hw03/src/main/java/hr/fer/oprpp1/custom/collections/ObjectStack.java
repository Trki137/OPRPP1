package hr.fer.oprpp1.custom.collections;

/**
 *
 * Class that implements stack via our own ArrayIndexCollection
 *
 * @author Dean Trkulja
 * @version 1.0
 */


public class ObjectStack<T> {
    /**
     * instance of ArrayIndexedCollection will represent in some ways stack
     */
    private ArrayIndexedCollection<T> stack;

    /**
     * Initializes stack
     */
    @SuppressWarnings("Unchecked")
    public ObjectStack(){
        stack = new ArrayIndexedCollection<>();
    }

    /**
     * Checks if stack is empty or not
     *
     * @return true if empty else false
     */

    public boolean isEmpty(){
        return stack.isEmpty();
    }

    /**
     * @return number of elements in stack
     */

    public int size(){
        return stack.size();
    }

    /**
     * Stores element at top of stack
     *
     * Throws NullPointerException if {@param value} is null
     *
     * @param value elemnt that we want to store in stack
     */

    public void push(T value){
        if(value == null)throw new NullPointerException();

        stack.add(value);
    }

    /**
     * Removes element from to of stack
     *
     * @return element from top of stack
     */
    public T pop(){
        if(this.size() == 0) throw new EmptyStackException();
        T elem = peek();
        stack.remove(size() - 1);
        return elem;
    }

    /**
     * Return element from top of stack, but it is not deleted
     *
     * @return element from top of stack
     */
    public T peek(){
        if(this.size() == 0) throw new EmptyStackException();
        return stack.get(size() - 1);
    }

    /**
     * Removes all elements from stack
     */
    public void clear(){
        stack.clear();
    }


}
