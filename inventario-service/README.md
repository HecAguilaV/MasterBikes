<p align="right">
  <img src="../logo.svg" alt="MasterBikes Logo" width="120"/>
</p>

# Inventario Service - MasterBikes

## Descripción
Microservicio encargado de la gestión de inventario de bicicletas y componentes por sucursal.

## Endpoints principales

- `GET /inventario/stock/{idBicicleta}`  
  Consulta el stock de una bicicleta por sucursal.
- `POST /inventario/actualizar`  
  Actualiza el stock de una bicicleta (por ejemplo, tras una venta o reposición).
  
  **Body ejemplo:**
  ```json
  {
    "idBicicleta": 1,
    "sucursal": "CASA_MATRIZ",
    "cantidad": -1
  }
  ```

## Poblamiento de inventario

### JSON para Postman
```json
[
  { "idBicicleta": 1, "sucursal": "CASA_MATRIZ", "cantidad": 10 },
  { "idBicicleta": 2, "sucursal": "SUCURSAL_1", "cantidad": 5 }
]
```

### SQL directo (ejemplo para MySQL/MariaDB)
```sql
INSERT INTO inventario (id_bicicleta, sucursal, cantidad) VALUES
(1, 'CASA_MATRIZ', 10),
(2, 'SUCURSAL_1', 5);
```

## Notas
- El stock se descuenta automáticamente al registrar una venta.
- Puedes poblar el inventario usando Postman o directamente en la base de datos.


## 🚀 Pruebas en vivo con Swagger y HATEOAS

Sigue estos pasos para demostrar el funcionamiento real del Inventario Service en una presentación o defensa:

### 1. Consultar stock de una bicicleta
- Abre [http://localhost:8084/swagger-ui.html](http://localhost:8084/swagger-ui.html)
- Prueba el endpoint `GET /inventario/stock/{idBicicleta}`.

### 2. Actualizar stock
- Usa `POST /inventario/actualizar`.
- Ejemplo de body:
  ```json
  {
    "idBicicleta": 1,
    "sucursal": "CASA_MATRIZ",
    "cantidad": -1
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
