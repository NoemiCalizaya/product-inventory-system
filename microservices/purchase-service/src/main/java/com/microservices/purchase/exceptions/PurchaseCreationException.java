package com.microservices.purchase.exceptions;

public class PurchaseCreationException extends RuntimeException {
    
    // Constructor con un mensaje de error
    public PurchaseCreationException(String message) {
        super(message);
    }

    // Constructor con un mensaje de error y una causa (excepción interna)
    public PurchaseCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

