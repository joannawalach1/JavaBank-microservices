package com.banking.userservice.exceptions;

import com.banking.userservice.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class UserServiceExceptionHandler {

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<ErrorResponse> handleNotFound(UserNotFound ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponse errorResponse = getErrorResponse(ex, request, status);
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(UserWithThatEmailExists.class)
    public ResponseEntity<ErrorResponse> handleUserExists(UserWithThatEmailExists ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        ErrorResponse errorResponse = getErrorResponse(ex, request, status);
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(NoDataException.class)
    public ResponseEntity<ErrorResponse> handleNoData(NoDataException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = getErrorResponse(ex, request, status);
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(InvalidLoginData.class)
    public ResponseEntity<ErrorResponse> handleInvalidLoginData(InvalidLoginData ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = getErrorResponse(ex, request, status);
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        Map<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                validationErrors.put(error.getField(), error.getDefaultMessage())
        );

        ErrorResponse errorResponse = getErrorResponse(ex, request, status, validationErrors);
        return new ResponseEntity<>(errorResponse, status);
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