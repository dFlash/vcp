package online.study.vcp.exception;

/**
 * Custom application exception
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = 6688313808054022325L;

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
