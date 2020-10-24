package com.newsinfo.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private final String message;
    private final List<String> details;
}
