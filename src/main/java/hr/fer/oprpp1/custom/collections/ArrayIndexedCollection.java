package hr.fer.oprpp1.custom.collections;

import java.nio.charset.UnsupportedCharsetException;
import java.util.Arrays;

public class ArrayIndexedCollection extends Collection{

    private int size; //current size of collection
    private Object[] elements;

    private static final int DEFAULT_ARRAY_SIZE = 16;



    public ArrayIndexedCollection(Collection collection, int initialSize){
        if(initialSize < 1) throw new IllegalArgumentException();
        if(collection == null) throw new NullPointerException();

        elements = new Object[Math.max(initialSize,collection.size())];
        addAll(collection);

        this.size = collection.size();


    }

    public ArrayIndexedCollection(Collection collection){
        this(collection,DEFAULT_ARRAY_SIZE);
    }

    public ArrayIndexedCollection(int initialCapacity){
        if(initialCapacity < 1) throw new IllegalArgumentException();

        size = 0;
        elements = new Object[initialCapacity];
    }

    public ArrayIndexedCollection(){
        this(DEFAULT_ARRAY_SIZE);
    }




    public void insert(Object value, int position){
        if(position < 0 || position > size) throw new IndexOutOfBoundsException();

        if(position == size) add(value);
        else{

            Object helpVariable = elements[position];
            Object secondHelpVariable;

            elements[position] = value;

            for(int i = position + 1; i <= size; i++){

                secondHelpVariable = elements[i];
                elements[i] = helpVariable;
                helpVariable = secondHelpVariable;

            }

            size++;

        }
    }

    public Object get(int index){
        if(index >= 0 && index <= size-1) return elements[index];
        throw new IndexOutOfBoundsException();
    }

    public int indexOf(Object value){
        if(value == null) return -1;

        for(int i = 0; i < size; i++)
            if(elements[i].equals(value)) return i;

        return -1;
    }

    public void remove(int index){
        if(index < 0 || index > size - 1) throw new IllegalArgumentException();

        for(int i = index; i < size; i++){

            if(i  == size-1)
                elements[i-1] = null;

            elements[i] = elements[i+1];
        }
        size--;
    }

    @Override
    public int size(){
        return  this.size;
    }

    @Override
    public boolean contains(Object value){
        return indexOf(value) != -1 ? true: false;
    }

    @Override
    public boolean remove(Object value){
        int index = indexOf(value);
        if (index == -1) return false;
        remove(index);
        return true;
    }

    @Override

    public Object[] toArray(){
        return Arrays.copyOf(elements, size);
    }

    @Override
    public void add(Object value){
        if(value == null) throw new NullPointerException();

        if(size >= elements.length){
            Object[] newArray = new Object[elements.length * 2];

            for (int i = 0; i < elements.length; i++)
                newArray[i] = elements[i];

            this.elements = newArray;
        }

        elements[size++] = value;

    }

    @Override
    public void clear(){

        for(int i = 0; i < size; i++)
            elements[i] = null;
        size = 0;

    }

    @Override
    public void forEach(Processor processor){

        for(int i = 0; i < this.size; i++)
            processor.process(this.elements[i]);

    }


}
