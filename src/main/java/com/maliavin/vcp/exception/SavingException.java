package com.maliavin.vcp.exception;

/**
 * Custom application exception which describes errors in saving entities
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
public class SavingException extends ApplicationException{

    private static final long serialVersionUID = -4944319887187455339L;

    public SavingException(String message) {
        super(message);
    }

}
