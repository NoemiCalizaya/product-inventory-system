
# Sistema de Gestión de Inventario

Un sistema integral de gestión de inventario construido con Spring Boot que maneja productos, compras, ventas y seguimiento de inventario.

## Características

- **Gestión de Productos**
  - Crear, actualizar y eliminar productos
  - Categorizar productos
  - Seguimiento de niveles de stock
  - Establecer márgenes de beneficio

- **Gestión de Compras**
  - Registrar compras de proveedores
  - Gestionar lotes de compra
  - Seguimiento de costos de compra
  - Gestión de proveedores

- **Gestión de Ventas**
  - Procesar transacciones de venta
  - Seguimiento de ventas por vendedor
  - Cálculo de beneficios
  - Generar informes de ventas

- **Seguimiento de Inventario**
  - Niveles de stock en tiempo real
  - Seguimiento de lotes
  - Monitoreo de fechas de vencimiento
  - Alertas de stock bajo

## Stack Tecnológico

- Java 17
- Spring Boot 3.2.3
- PostgreSQL
- Maven
- Docker
- Swagger/OpenAPI

## Comenzando

### Prerrequisitos

- JDK 17
- Maven
- Docker y Docker Compose
- PostgreSQL (si se ejecuta localmente)

### Ejecutar con Docker

```bash
# Construir la aplicación
mvn clean package -DskipTests

# Iniciar la aplicación y la base de datos
docker-compose up --build
```

### Documentación de la API

La documentación de la API está disponible en:
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs
- OpenAPI YAML: http://localhost:8080/v3/api-docs.yaml

### Comandos Docker

```bash
# Iniciar servicios
docker-compose up --build

# Iniciar servicios en segundo plano
docker-compose up -d

# Detener servicios
docker-compose down

# Detener servicios y eliminar volúmenes
docker-compose down -v

# Ver logs
docker-compose logs

# Ver logs en tiempo real
docker-compose logs -f

# Ver estado de los servicios
docker-compose ps
```

