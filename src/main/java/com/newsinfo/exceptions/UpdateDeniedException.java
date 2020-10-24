package com.newsinfo.exceptions;

public class UpdateDeniedException extends RuntimeException {
    public UpdateDeniedException(String errorMessage) {
        super(errorMessage);
    }
}
