package hr.fer.oprpp1.custom.collections;

public class EmptyStackException extends RuntimeException{
    public EmptyStackException(){
        super();
    }
    public EmptyStackException(String description){
        super(description);
    }
}
