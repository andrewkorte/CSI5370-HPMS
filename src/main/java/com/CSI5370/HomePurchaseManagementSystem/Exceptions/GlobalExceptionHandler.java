package com.CSI5370.HomePurchaseManagementSystem.Exceptions;

import com.CSI5370.HomePurchaseManagementSystem.Domain.Home;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PurchaseNotPossibleException.class)
    public ResponseEntity<Map<String, Object>> handleTestingException(PurchaseNotPossibleException ex){
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
        response.put("error", HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }




    @ExceptionHandler(PurchaseNotPossibleException.class)
    public ResponseEntity<Map<String, Object>> handleNotPossibleException(PurchaseNotPossibleException ex){
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PostgresUnavailableException.class)
    public ResponseEntity<Map<String, Object>> handlePostgresUnavailableException(PostgresUnavailableException ex){
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
        response.put("error", HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(CustomerNotFound.class)
    public ResponseEntity<Map<String, Object>> handleCustomerNotFoundException(CustomerNotFound ex){
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HomeNotFound.class)
    public ResponseEntity<Map<String, Object>> handleHomeNotFoundException(HomeNotFound ex){
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RealtorNotFound.class)
    public ResponseEntity<Map<String, Object>> handleRealtorNotFoundException(RealtorNotFound ex){
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PurchaseNotFound.class)
    public ResponseEntity<Map<String, Object>> handlePurchaseNotFoundException(PurchaseNotFound ex){
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
