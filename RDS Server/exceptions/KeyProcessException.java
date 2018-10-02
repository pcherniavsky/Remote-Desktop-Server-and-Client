package exceptions;

/**
 * The exception used with key process issues.
 * 
 * @author Peter Cherniavsky
 */
public class KeyProcessException extends Exception {
    /**
     * The constructor used to created the exception.
     * 
     * @param message The message associated with the exception.
     */
    public KeyProcessException(String message) {
        super(message);
    }
}
