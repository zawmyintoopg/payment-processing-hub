package com.mybank.paymenthub.exception;

public class IDNotFoundException extends RuntimeException {
    public IDNotFoundException(String message){
        super(message);
    }
}
