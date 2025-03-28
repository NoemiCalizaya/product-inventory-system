
# Microservicios para un Sistema de Gestión de Inventario de Productos

Un sistema integral de gestión de inventario construido con Spring Boot que maneja usuarios, productos, compras, ventas y seguimiento de inventario.

### Ejecutar con Docker

```bash
# Iniciar la aplicación y la base de datos
docker-compose up --build
```

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
## MICROSERVICES

### SERVICE USERS
POST: http://localhost:8081/api/salesmen
{
  "ci": "12345679",
  "name": "John Orellana",
  "email": "john.orellana@gmail.com",
  "state": "ACTIVE"
}

GET: http://localhost:8081/api/salesmen

### SERVICE PRODUCTS

CREATE CATEGORIES
POST: http://localhost:8084/api/categories
{
    "description": "Bebidas",
    "state": "ACTIVE"
}
CREATE PRODUCTS
POST: http://localhost:8084/api/products
{
    "productCod": "LECHE001",
    "category": {
        "categoryId": 2
    },
    "productName": "LECHE 1L",
    "salePrice": 10.00,
    "unitMeasure": "UNIT",
    "profitMargin": 0.20,
    "state": "ACTIVE"
}

### SERVICE PURCHASES

CREATE SUPPLIERS
POST: http://localhost:8085/api/suppliers
{
    "supplierName": "Distribuidora XYZ",
    "phoneNumber": "+591 77712345",
    "address": "Av. América #123, La Paz",
    "state": "ACTIVE"
}

CREATE PURCHASES
POST: http://localhost:8085/api/purchases
{
  "salesmanCi": "12345679",
  "supplierId": 1,
  "batches": [
    {
      "batchNumber": "LECHE001",
      "productCod": "LECHE001",
      "quantity": 30,
      "availableQuantity": 30,
      "costUnit": 6.00,
      "expirationDate": "2024-12-31",
      "manufacturingDate": "2025-03-22",
      "state": "ACTIVE"
    }
  ]
}

### SERVICE SALES

CREATE SALES
POST: http://localhost:8082/api/sales
{
    "salesmanCi": "12345679",
    "saleDetails": [
        {
            "productCod": "ARR001",
            "quantity": 10
        },
        {
            "productCod": "COCA001",
            "quantity": 5
        }
    ]
}
