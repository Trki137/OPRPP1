package hr.fer.oprpp1.hw05.shell;
/**
 * Thrown when reading or writing from environment failed
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public class ShellIOException extends Exception {
    /**
     * Default constructor
     */
    public ShellIOException() {
    }

    /**
     * Constructor with detail message
     * @param message the detail message
     */
    public ShellIOException(String message) {
        super(message);
    }
}
