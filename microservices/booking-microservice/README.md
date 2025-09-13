# Booking Microservice

Este microservicio maneja las reservas y órdenes, verificando la disponibilidad de stock antes de procesar las órdenes.

## Cómo funciona el sistema

### 1. Flujo de procesamiento de órdenes

```
Cliente → POST /api/booking/order → Verificar Stock → Guardar Orden → Respuesta
```

### 2. Verificación de stock

- El servicio llama al `StockClient` para verificar la disponibilidad de cada item
- Si algún item no tiene stock, la orden se rechaza
- Si todos los items tienen stock, la orden se guarda en la base de datos

### 3. Manejo de errores

#### Circuit Breaker
- **Configurado**: `@CircuitBreaker(name = "stockService", fallbackMethod = "fallbackToStockService")`
- **Fallback**: Cuando el servicio de stock no está disponible, se ejecuta el método de fallback
- **Configuración**: Se abre después de 50% de fallos en 10 llamadas

#### Fallback Strategy
- **Stock Service Down**: Retorna "Stock service is currently unavailable"
- **Stock Insuficiente**: Retorna "Insufficient stock for items: [lista de items]"
- **Error Interno**: Retorna "Internal server error occurred"

### 4. Configuración de Feign

- **Timeout**: 5 segundos para conexión y lectura
- **Retry**: 3 intentos con espera exponencial
- **Logging**: Nivel FULL para debugging
- **Error Decoder**: Manejo personalizado de códigos HTTP

## Resolución del problema "Order Cannot be Saved or Stock service unavailable"

### Problemas identificados:

1. **Manejo inadecuado de excepciones**: El controlador original capturaba excepciones pero no las manejaba correctamente
2. **Fallback no implementado**: El Circuit Breaker estaba configurado pero no funcionaba
3. **Lógica mezclada**: La lógica de negocio estaba en el controlador
4. **Falta de validaciones**: No había validaciones de entrada

### Soluciones implementadas:

1. **Separación de responsabilidades**:
   - `BookingController`: Maneja HTTP requests/responses
   - `BookingService`: Lógica de negocio
   - `StockClient`: Comunicación con servicio de stock

2. **Manejo robusto de errores**:
   - Circuit Breaker funcional con fallback
   - Manejo específico de diferentes tipos de error
   - Logging detallado para debugging

3. **Configuración de Feign**:
   - Timeouts configurables
   - Reintentos automáticos
   - Decodificador de errores personalizado

4. **Validaciones**:
   - Verificación de datos de entrada
   - Validación de stock antes de guardar
   - Identificación específica de items sin stock

## Endpoints disponibles

### POST /api/booking/order
- **Propósito**: Crear una nueva orden
- **Body**: `OrderDTO` con lista de `OrderItem`
- **Respuestas**:
  - `200 OK`: Orden guardada exitosamente
  - `400 Bad Request`: Datos de entrada inválidos
  - `409 Conflict`: Stock insuficiente
  - `503 Service Unavailable`: Servicio de stock no disponible
  - `500 Internal Server Error`: Error interno del servidor

### GET /api/booking/health
- **Propósito**: Verificar el estado del servicio
- **Respuestas**:
  - `200 OK`: Servicio saludable
  - `503 Service Unavailable`: Servicio no saludable

## Configuración

### Circuit Breaker
```properties
resilience4j.circuitbreaker.instances.stockService.sliding-window-size=10
resilience4j.circuitbreaker.instances.stockService.failure-rate-threshold=50
resilience4j.circuitbreaker.instances.stockService.wait-duration-in-open-state=30000
```

### Feign
```properties
feign.client.config.default.connectTimeout=5000
feign.client.config.default.readTimeout=5000
feign.client.config.default.loggerLevel=full
```

## Testing

Para probar el sistema:

1. **Servicio de stock disponible**:
   ```bash
   curl -X POST http://localhost:8080/api/booking/order \
     -H "Content-Type: application/json" \
     -d '{"orderItems":[{"code":"ITEM001","price":10.99,"orderQty":2}]}'
   ```

2. **Verificar salud del servicio**:
   ```bash
   curl http://localhost:8080/api/booking/health
   ```

## Troubleshooting

### "Order Cannot be Saved or Stock service unavailable"

**Causas posibles**:
1. Servicio de stock no está ejecutándose
2. Problemas de red entre microservicios
3. Timeout en la comunicación
4. Circuit Breaker abierto

**Soluciones**:
1. Verificar que el servicio de stock esté ejecutándose
2. Revisar logs para identificar errores específicos
3. Verificar configuración de Eureka/Service Discovery
4. Revisar configuración de Circuit Breaker

### Logs importantes

- **Stock verification**: `Stock check for item [CODE]: [true/false]`
- **Circuit Breaker**: `Stock service unavailable, using fallback method`
- **Order processing**: `Processing order with [X] items`
- **Order saved**: `Order saved successfully with ID: [ID]`
