# Sucursal Service - MasterBikes

## Descripción
Microservicio encargado de la gestión de sucursales de MasterBikes.

## Endpoints principales

- `GET /sucursal/sucursales`  
  Lista todas las sucursales.
- `POST /sucursal/crear`  
  Crea una nueva sucursal.
  
  **Body ejemplo:**
  ```json
  {
    "nombre": "CASA_MATRIZ",
    "direccion": "Av. Principal 123, Santiago",
    "telefono": "+56 2 2345 6789"
  }
  ```

## Poblamiento de sucursales

### JSON para Postman
```json
[
  { "nombre": "CASA_MATRIZ", "direccion": "Av. Principal 123, Santiago", "telefono": "+56 2 2345 6789" },
  { "nombre": "SUCURSAL_1", "direccion": "Av. Secundaria 456, Viña del Mar", "telefono": "+56 32 1234 5678" }
]
```

### SQL directo (ejemplo para MySQL/MariaDB)
```sql
INSERT INTO sucursal (nombre, direccion, telefono) VALUES
('CASA_MATRIZ', 'Av. Principal 123, Santiago', '+56 2 2345 6789'),
('SUCURSAL_1', 'Av. Secundaria 456, Viña del Mar', '+56 32 1234 5678');
```

## Notas
- Las sucursales se deben poblar antes de poblar inventario o ventas.
- Puedes poblar usando Postman o directamente en la base de datos.

## Pruebas rápidas en Postman
1. Consulta las sucursales disponibles.
2. Crea una nueva sucursal.

---

> Para más detalles de integración, revisa el README general.
