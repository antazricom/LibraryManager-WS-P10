package com.antazri.exception;

import com.antazri.model.bean.Fault;

public class ExtendException extends Exception {

    private Fault fault;

    public Fault getFault() {
        return fault;
    }

    public ExtendException(String message, Fault fault) {
        super(message);
        this.fault = fault;
    }

    public ExtendException(String message, Throwable cause, Fault fault) {
        super(message, cause);
        this.fault = fault;
    }
}
