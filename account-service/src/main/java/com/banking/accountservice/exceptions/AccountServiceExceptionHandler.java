package com.banking.accountservice.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class AccountServiceExceptionHandler {

    @ExceptionHandler(AccountNotFound.class)
    public ResponseEntity<Map<String, Object>> handleAccountNotFound(AccountNotFound ex, HttpServletRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        HttpStatus status = HttpStatus.NOT_FOUND;
        body.put("timestamp", ZonedDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", ex.getMessage());
        body.put("path", request.getRequestURI());
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(DuplicateAccountNumberException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateAccountNumberException(DuplicateAccountNumberException ex, HttpServletRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        HttpStatus status = HttpStatus.CONFLICT;
        body.put("timestamp", ZonedDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", ex.getMessage());
        body.put("path", request.getRequestURI());
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorizedAccessException(UnauthorizedAccessException ex, HttpServletRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        HttpStatus status = HttpStatus.FORBIDDEN;
        body.put("timestamp", ZonedDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", ex.getMessage());
        body.put("path", request.getRequestURI());
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(InvalidCurrencyException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidCurrencyException(InvalidCurrencyException ex, HttpServletRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        body.put("timestamp", ZonedDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", ex.getMessage());
        body.put("path", request.getRequestURI());
        return new ResponseEntity<>(body, status);
    }
}