package com.newsinfo.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleExceptions(Throwable throwable) {
        ErrorResponse error;
        List<String> details = new ArrayList<>();
        details.add(throwable.getLocalizedMessage());
        error = new ErrorResponse(throwable.getMessage(), details);
        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
    }
}
