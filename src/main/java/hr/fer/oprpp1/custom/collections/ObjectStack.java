package hr.fer.oprpp1.custom.collections;



public class ObjectStack {
    ArrayIndexedCollection stack;

    public ObjectStack(){
        stack = new ArrayIndexedCollection();
    }

    public boolean isEmpty(){
        return stack.isEmpty();
    }

    public int size(){
        return stack.size();
    }

    public void push(Object value){
        if(value == null)throw new NullPointerException();

        stack.add(value);
    }

    public Object pop(){
        if(this.size() == 0) throw new EmptyStackException();
        Object elem = peek();
        stack.remove(size() - 1);
        return elem;
    }

    public Object peek(){
        if(this.size() == 0) throw new EmptyStackException();
        return stack.get(size() - 1);
    }

    public void clear(){
        stack.clear();
    }


}
