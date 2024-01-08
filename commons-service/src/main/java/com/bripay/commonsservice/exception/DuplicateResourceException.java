package com.bripay.commonsservice.exception;


/** Esta Excepción se utilizara cuando se identifique un recurso que ya se encuentra registrado **/

public class DuplicateResourceException extends RuntimeException{
    public DuplicateResourceException(String message){
        super(message);
    }
}
