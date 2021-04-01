package dbconsent;

/**
 * An exception for when we realise we have reached an invalid state -
 * ie, there is a variable in the head of the given query that is not in the body.
 *
 * This should only happen during parsing, but it's good to check when using PoolOfNames' safeNamespace function too.
 *
 */
public class HeadVariableNotInQueryException extends Throwable {

    /**
     * @param message the message to print
     */
    public HeadVariableNotInQueryException(String message){
        super(message);
    }

}
