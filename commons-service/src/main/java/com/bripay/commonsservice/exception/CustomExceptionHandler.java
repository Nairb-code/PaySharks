package com.bripay.commonsservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;


/** En esta sección se manejarán todas las excepciones **/
@Component
@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDetail> handleAllExceptions(Exception ex, WebRequest request){
        ExceptionDetail errorDetails = new ExceptionDetail(
                "General Exception: " + ex.getMessage(),
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /** RECURSO NO ENCONTRADO.**/
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDetail> handlerResourceNotFound(ResourceNotFoundException exception, WebRequest request){
        ExceptionDetail exceptionDetail = new ExceptionDetail(
                exception.getMessage(),
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND,
                request.getDescription(false)
        );

        return new ResponseEntity<>(exceptionDetail, exceptionDetail.getHttpStatus());
    }

    /** EL RECURSO YA SE ENCUENTRA REGISTRADO**/
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ExceptionDetail> handlerDuplicateResourceException(DuplicateResourceException exception, WebRequest request){
        ExceptionDetail exceptionDetail = new ExceptionDetail(
                exception.getMessage(),
                LocalDateTime.now(),
                HttpStatus.CONFLICT,
                request.getDescription(false)
        );

        return new ResponseEntity<>(exceptionDetail, exceptionDetail.getHttpStatus());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDetail> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            WebRequest request
    ){
        ExceptionDetail errorDetails = new ExceptionDetail(
                "Total error " + ex.getErrorCount() + " : First error: " + ex.getFieldError().getDefaultMessage(),
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }


}
