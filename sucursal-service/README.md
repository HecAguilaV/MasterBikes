<p align="right">
  <img src="../logo.svg" alt="MasterBikes Logo" width="120"/>
</p>

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


## 🚀 Pruebas en vivo con Swagger y HATEOAS

Sigue estos pasos para demostrar el funcionamiento real del Sucursal Service en una presentación o defensa:

### 1. Consultar sucursales
- Abre [http://localhost:8083/swagger-ui.html](http://localhost:8083/swagger-ui.html)
- Prueba el endpoint `GET /sucursal/sucursales`.

### 2. Crear una sucursal
- Usa `POST /sucursal/crear`.
- Ejemplo de body:
  ```json
  {
    "nombre": "CASA_MATRIZ",
    "direccion": "Av. Principal 123, Santiago",
    "telefono": "+56 2 2345 6789"
  }
  ```

### 3. Pruebas automáticas
- Ejecuta:
  ```sh
  mvn test
  ```
- Verifica que todos los tests pasen (`BUILD SUCCESS`).

---

> Todos los endpoints y ejemplos pueden ser probados en vivo desde Swagger UI o Postman.
