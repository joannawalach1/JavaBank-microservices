package com.banking.userservice.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jnr.ffi.annotations.In;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.Map;


@RestControllerAdvice
public class UserServiceExceptionHandler {


    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(UserNotFound ex, HttpServletRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        HttpStatus status = HttpStatus.NOT_FOUND;
        body.put("timestamp", ZonedDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", ex.getMessage());
        body.put("path", request.getRequestURI());
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(UserWithThatEmailExists.class)
    public ResponseEntity<Map<String, Object>> handleUserExists(UserWithThatEmailExists ex, HttpServletRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        HttpStatus status = HttpStatus.CONFLICT;
        body.put("timestamp", ZonedDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", ex.getMessage());
        body.put("path", request.getRequestURI());
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(NoDataException.class)
    public ResponseEntity<Map<String, Object>> handleNoData(NoDataException ex, HttpServletRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        body.put("timestamp", ZonedDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", ex.getMessage());
        body.put("path", request.getRequestURI());
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(InvalidLoginData.class)
    public ResponseEntity<Map<String, Object>> handleInvalidLoginData(InvalidLoginData ex, HttpServletRequest request) {
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
