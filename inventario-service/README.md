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

## Pruebas rápidas en Postman
1. Consulta el stock de una bicicleta.
2. Actualiza el stock tras una venta.

---

> Para más detalles de integración, revisa el README general y el del microservicio de ventas.
