package hr.fer.oprpp1.custom.collections;

import java.nio.charset.UnsupportedCharsetException;

public class Collection {
    protected Collection(){

    }

    public boolean isEmpty(){
        return this.size() > 0 ? false : true;
    }

    public int size(){
        return 0;
    }

    public void add(Object value){

    }

    public boolean contains(Object value){
        return false;
    }

    public boolean remove(Object value){
        return false;
    }

    public Object[] toArray(){
        throw new UnsupportedCharsetException("");
    }

    public void forEach(Processor processor){

    }

    public void addAll(Collection other){

        if(other == null)
            throw new NullPointerException();

        class AddCollectionProcessor extends Processor{
            @Override
            public void process(Object value){
                add(value);
            }
        }

        other.forEach(new AddCollectionProcessor());

    }

    public void clear(){

    }

}
