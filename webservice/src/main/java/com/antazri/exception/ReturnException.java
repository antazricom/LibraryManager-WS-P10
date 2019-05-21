package com.antazri.exception;

import com.antazri.model.bean.Fault;

public class ReturnException extends Exception {

    private Fault fault;

    public Fault getFault() {
        return fault;
    }

    public ReturnException(String message, Fault fault) {
        super(message);
        this.fault = fault;
    }

    public ReturnException(String message, Throwable cause, Fault fault) {
        super(message, cause);
        this.fault = fault;
    }
}
