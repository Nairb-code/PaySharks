package com.bripay.commonsservice.exception;

public class NullOrEmptyFieldException extends RuntimeException{
    public NullOrEmptyFieldException(String message){
        super(message);
    }
}
