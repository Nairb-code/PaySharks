package com.bripay.commonsservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class ExceptionDetail {
    private String message;
    private LocalDateTime timestamp;
    private HttpStatus httpStatus;
    private String path;
}
