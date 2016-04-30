package com.maliavin.vcp.exception;

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
