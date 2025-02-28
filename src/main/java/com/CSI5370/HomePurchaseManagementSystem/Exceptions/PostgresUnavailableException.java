package com.CSI5370.HomePurchaseManagementSystem.Exceptions;

public class PostgresUnavailableException extends RuntimeException {
    public PostgresUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
