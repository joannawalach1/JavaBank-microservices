package com.banking.accountservice.exceptions;

import com.banking.accountservice.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AccountServiceExceptionHandler {


    @ExceptionHandler(AccountNotFound.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFound(AccountNotFound ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponse errorResponse = getErrorResponse(ex, request, status);
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(DuplicateAccountNumberException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateAccountNumberException(DuplicateAccountNumberException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        ErrorResponse errorResponse = getErrorResponse(ex, request, status);
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedAccessException(UnauthorizedAccessException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        ErrorResponse errorResponse = getErrorResponse(ex, request, status);
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(InvalidCurrencyException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCurrencyException(InvalidCurrencyException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = getErrorResponse(ex, request, status);
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                validationErrors.put(error.getField(), error.getDefaultMessage())
        );

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = getErrorResponse(ex, request, status, validationErrors);
        return ResponseEntity.status(status).body(errorResponse);
    }

    private static ErrorResponse getErrorResponse(Exception ex, HttpServletRequest request, HttpStatus status) {
        return new ErrorResponse(
                ZonedDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                null);
    }

    private static ErrorResponse getErrorResponse(Exception ex, HttpServletRequest request, HttpStatus status, Map<String, String> validationErrors) {
        return new ErrorResponse(
                ZonedDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                validationErrors);
    }
}