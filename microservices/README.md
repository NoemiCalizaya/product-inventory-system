# Microservicios — Product Inventory System

Documentación general del proyecto: [README en la raíz](../README.md).

Arquitectura desplegable con **Docker Compose**: PostgreSQL + cinco servicios Spring Boot (usuarios, ventas, inventario, productos, compras).

### Ejecutar con Docker

```bash
cp .env.example .env   # editar credenciales
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