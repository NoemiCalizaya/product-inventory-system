package com.BD.Demo.exceptions;

public class InsufficientStockException extends RuntimeException{
    public InsufficientStockException(String mensaje) {
        super(mensaje);
    }
}
