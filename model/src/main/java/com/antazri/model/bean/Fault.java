package com.antazri.model.bean;

public class Fault {

    private String faultCode;
    private String faultString;

    public Fault() {
    }

    public Fault(String faultCode, String faultString) {
        this.faultCode = faultCode;
        this.faultString = faultString;
    }

    public String getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }

    public String getFaultString() {
        return faultString;
    }

    public void setFaultString(String faultString) {
        this.faultString = faultString;
    }
}
