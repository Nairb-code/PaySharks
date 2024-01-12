package com.bripay.commonsservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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

    /** EL CAMPO SOLICITADO NO DEBE SER NULA O ESTAR VACIA. **/
    @ExceptionHandler(NullOrEmptyFieldException.class)
    public ResponseEntity<ExceptionDetail> handlerNullOrEmptyFieldException(NullOrEmptyFieldException exception, WebRequest request){
        ExceptionDetail exceptionDetail = new ExceptionDetail(
                exception.getMessage(),
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
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

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionDetail> handleTypeMismatchException(
            MethodArgumentTypeMismatchException ex,
            WebRequest request) {
        ExceptionDetail exceptionDetail = new ExceptionDetail(
                ex.getName() +  ": El tipo de dato recibido no puede ser convertido al requerido.",
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                request.getDescription(false)
        );
        return ResponseEntity.badRequest().body(exceptionDetail);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionDetail> handleNotReadableException(
            HttpMessageNotReadableException ex,
            WebRequest request
    ) {
        ExceptionDetail exceptionDetail = new ExceptionDetail(
                ex.getMessage() + ": El cuerpo de la solicitud no pudo ser leído.",
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                request.getDescription(false)
        );
        return ResponseEntity.badRequest().body(exceptionDetail);
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ExceptionDetail> handleMissingPathVariable(
            MissingPathVariableException ex,
            WebRequest request) {
        ExceptionDetail exceptionDetail = new ExceptionDetail(
                ex.getMessage() + ": El valor para el parámetro de ruta no se proporcionó.",
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                request.getDescription(false)
        );

        return new ResponseEntity<>(exceptionDetail, exceptionDetail.getHttpStatus());
    }
}
