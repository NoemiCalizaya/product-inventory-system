package com.microservices.sales.exceptions;

public class SalesmanNotFoundException extends RuntimeException{
    public SalesmanNotFoundException(String mensaje) {
        super(mensaje);
    }
}
