package com.newsinfo.exceptions;

public class PollException extends RuntimeException {
    public PollException(String errorMessage) {
        super(errorMessage);
    }
}
