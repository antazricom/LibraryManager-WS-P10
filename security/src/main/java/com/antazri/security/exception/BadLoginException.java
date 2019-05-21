package com.antazri.security.exception;

import com.antazri.model.bean.Fault;

public class BadLoginException extends Exception {

    private Fault fault;

    public Fault getFault() {
        return fault;
    }

    public BadLoginException(String message, Fault fault) {
        super(message);
        this.fault = fault;
    }

    public BadLoginException(String message, Throwable cause, Fault fault) {
        super(message, cause);
        this.fault = fault;
    }
}
