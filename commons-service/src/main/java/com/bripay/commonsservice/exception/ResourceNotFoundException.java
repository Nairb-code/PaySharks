package com.bripay.commonsservice.exception;

/** Esta excepción se utilizará cuando se intente acceder a un recurso
 * que no se encuentra registrado en la aplicación.**/
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
