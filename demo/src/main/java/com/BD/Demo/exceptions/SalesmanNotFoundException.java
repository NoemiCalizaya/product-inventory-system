package com.BD.Demo.exceptions;

public class SalesmanNotFoundException extends RuntimeException{
    
    public SalesmanNotFoundException(String mensaje) {
        super(mensaje);
    }
    
}
