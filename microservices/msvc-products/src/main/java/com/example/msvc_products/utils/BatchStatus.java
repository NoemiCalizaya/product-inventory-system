package com.example.msvc_products.utils;

// Enum para status del lote
public enum BatchStatus {
    ACTIVE,     // Lote activo y disponible
    EXPIRED,    // Lote vencido
    DEPLETED,   // Lote agotado
    BLOCKED,    // Lote bloqueado por calidad
    QUARANTINE  // Lote en cuarentena
}
