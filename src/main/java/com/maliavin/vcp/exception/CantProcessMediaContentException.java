package com.maliavin.vcp.exception;

/**
 * Custom application exception which specified for video/thumbnails operation
 * fails.
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public class CantProcessMediaContentException extends ApplicationException {

    private static final long serialVersionUID = 5088201555615698494L;

    public CantProcessMediaContentException(String message) {
        super(message);
    }

    public CantProcessMediaContentException(Throwable cause) {
        super(cause);
    }

    public CantProcessMediaContentException(String message, Throwable cause) {
        super(message, cause);
    }
}
