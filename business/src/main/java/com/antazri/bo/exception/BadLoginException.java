package com.antazri.bo.exception;

import com.antazri.model.bean.Fault;

/**
 * BadLoginException permet d'identifier les exceptions levées par les erreurs liées à l'identification de
 * l'utilisateur via le service Authentication
 */
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
