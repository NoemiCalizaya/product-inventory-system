package com.example.msvc_products.utils;

public enum MovementType {
    IN,                 // Entrada de mercancía
    OUT,                // Salida de mercancía
    TRANSFER_IN,        // Entrada por transferencia
    TRANSFER_OUT,       // Salida por transferencia
    ADJUSTMENT_IN,      // Ajuste positivo
    ADJUSTMENT_OUT,     // Ajuste negativo
    RETURN_IN,          // Devolución entrada
    RETURN_OUT          // Devolución salida
}
