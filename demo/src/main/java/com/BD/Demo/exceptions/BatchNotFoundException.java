package com.BD.Demo.exceptions;

public class BatchNotFoundException extends RuntimeException{
    public BatchNotFoundException(String mensaje) {
        super(mensaje);
    }
}
