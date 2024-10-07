package com.security.authservice.exception;


import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(UserExceptions.UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserExceptions.UserNotFoundException ex, WebRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("error", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(UserExceptions.EmailAlreadyExistsException.class)
    public ResponseEntity<Object> handleEmailAlreadyExistsException(UserExceptions.EmailAlreadyExistsException ex, WebRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("error", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(UserExceptions.EmailNotFoundException.class)
    public ResponseEntity<Object> handleEmailNotFoundException(UserExceptions.EmailNotFoundException ex, WebRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("error", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(UserExceptions.InvalidPasswordException.class)
    public ResponseEntity<Object> handleInvalidPasswordException(UserExceptions.InvalidPasswordException ex, WebRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("Erro:", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UserExceptions.EmptyFieldException.class)
    public ResponseEntity<Object> handleEmptyFieldException(UserExceptions.EmptyFieldException ex, WebRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("Erro:", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UserExceptions.PasswordTooShortException.class)
    public ResponseEntity<Object> handlePasswordTooShortException(UserExceptions.PasswordTooShortException ex, WebRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("Erro:", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UserExceptions.PasswordTooLongException.class)
    public ResponseEntity<Object> handlePasswordTooLongException(UserExceptions.PasswordTooLongException ex, WebRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("Erro:", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleValidationExceptions(ConstraintViolationException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException ex, WebRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("Erro:", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("Erro:", "Erro não mapeado");
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
