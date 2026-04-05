package com.example.spring_ai_demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * Global exception handler — exposes the real root cause in the HTTP response
 * so we can debug without digging through truncated server logs.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAll(Exception ex) {
        Throwable root = ex;
        while (root.getCause() != null) root = root.getCause();

        return ResponseEntity.status(500).body(Map.of(
                "error",     ex.getClass().getSimpleName(),
                "message",   ex.getMessage() != null ? ex.getMessage() : "null",
                "rootCause", root.getClass().getSimpleName() + ": " + root.getMessage()
        ));
    }
}
