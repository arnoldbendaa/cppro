package com.softproideas.util.validation;

public class MappingValidationException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Object[] errors;
    
    public MappingValidationException(String message, Object[] errorsMessages){
        super(message);
        this.errors = errorsMessages;
    }

    public Object[] getErrors() {
        return errors;
    }
}
