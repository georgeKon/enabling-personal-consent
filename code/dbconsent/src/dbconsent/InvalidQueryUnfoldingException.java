package dbconsent;

/**
 * An exception to be thrown when a query atom cannot be unfolded during consent propagation.
 *
 */

public class InvalidQueryUnfoldingException extends Exception {

    /**
     * @param message the message to print
     */
    InvalidQueryUnfoldingException(String message){
        super(message);
    }
    
}
