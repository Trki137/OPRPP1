package hr.fer.oprpp1.hw04.db;

/**
 * Exception class for StudentRecord class
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public class StudentRecordException extends RuntimeException{

    /**
     * Constructor with message
     *
     * @param message message
     */

    public StudentRecordException(String message){
        super(message);
    }
}
