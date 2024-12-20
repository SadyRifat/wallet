package com.digital.wallet.exception;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {SiteException.class})
    public ResponseEntity<Error> handle(SiteException exception) {
        Error model = new Error(exception.getHttpStatus(), exception.getMessage(), exception);
        printStackTrace(exception);
        return new ResponseEntity<>(model, exception.getHttpStatus());
    }

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Error> handle(BadRequestException exception) {
        Error model = new Error(HttpStatus.BAD_REQUEST, exception.getMessage(), exception.getCode(), exception);
        printStackTrace(exception);
        return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ValidationException.class})
    public ResponseEntity<Error> handle(ValidationException exception) {
        Error model = new Error(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);
        printStackTrace(exception);
        return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {BindException.class})
    public ResponseEntity<Error> handle(BindException exception) {
        Error model = new Error(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);
        printStackTrace(exception);
        return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<Error> handleValidationExceptions(HttpMessageNotReadableException exception) {
        Error model = new Error(HttpStatus.BAD_REQUEST, "Invalid request data", exception);
        printStackTrace(exception);
        return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<Error> handleValidationExceptions(ConstraintViolationException exception) {
        Error model = new Error(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);
        printStackTrace(exception);
        return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<Error> handleRuntimeExceptions(RuntimeException exception) {
        Error model = new Error(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
        printStackTrace(exception);
        return new ResponseEntity<>(model, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Error> handleDatabaseFindExceptions(ResourceNotFoundException exception) {
        Error model = new Error(HttpStatus.NOT_FOUND, exception.getMessage(), exception.getCode(), exception);
        printStackTrace(exception);
        return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UnauthorizedException.class})
    public ResponseEntity<Error> handleDatabaseFindExceptions(UnauthorizedException exception) {
        Error model = new Error(HttpStatus.UNAUTHORIZED, exception.getMessage(), HttpStatus.UNAUTHORIZED.value(), exception);
        printStackTrace(exception);
        return new ResponseEntity<>(model, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<Error> handleUnauthorizedException(AccessDeniedException exception) {
        Error model = new Error(HttpStatus.FORBIDDEN, exception.getMessage(), exception);
        printStackTrace(exception);
        return new ResponseEntity<>(model, HttpStatus.FORBIDDEN);
    }

    private void printStackTrace(Exception ex) {
        StackTraceElement[] trace = ex.getStackTrace();
        StringBuilder traceLines = new StringBuilder();
        traceLines.append("Caused By: ").append(ex.fillInStackTrace()).append("\n");
        Arrays.stream(trace).filter(f -> f.getClassName().contains("com.freebee.classroom"))
                .forEach(traceElement -> traceLines.append("\tat ").append(traceElement).append("\n"));
        log.error(traceLines.toString());
    }
}
