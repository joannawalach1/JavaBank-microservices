package com.banking.transactionservice.exceptions;

import com.banking.transactionservice.CustomErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class TransactionServiceExceptionHandler {

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleNotFound(TransactionNotFoundException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomErrorResponse errorResponse = getErrorResponse(ex, request, status);
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Map<String, String> validationErrors = ex.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        fieldError -> fieldError.getField(),
                        fieldError -> fieldError.getDefaultMessage(),
                        (existing, replacement) -> existing // gdy duplikaty
                ));
        CustomErrorResponse errorResponse = getErrorResponse(ex, request, status, validationErrors);
        return new ResponseEntity<>(errorResponse, status);
    }

    private static CustomErrorResponse getErrorResponse(Exception ex, HttpServletRequest request, HttpStatus status) {
        return new CustomErrorResponse(
                ZonedDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
    }

    private static CustomErrorResponse getErrorResponse(Exception ex, HttpServletRequest request, HttpStatus status, Map<String, String> validationErrors) {
        return new CustomErrorResponse(
                ZonedDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                "Validation failed",
                request.getRequestURI(),
                validationErrors
        );
    }
}