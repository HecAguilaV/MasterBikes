# Venta Service - MasterBikes

## Descripción
Microservicio encargado de registrar ventas y generar boletas. Cada venta descuenta stock en la sucursal correspondiente.

## Endpoints principales

- `POST /api/v1/ventas`  
  Registra una nueva venta.
  
  **Body ejemplo:**
  ```json
  {
    "idCliente": 1,
    "productos": [
      { "idBicicleta": 1, "cantidad": 1 }
    ],
    "total": 1850000,
    "sucursal": "CASA_MATRIZ"
  }
  ```

- `GET /api/v1/ventas`  
  Lista todas las ventas registradas.

## Poblamiento de ventas

### JSON para Postman
```json
[
  {
    "idCliente": 1,
    "productos": [ { "idBicicleta": 1, "cantidad": 1 } ],
    "total": 1850000,
    "sucursal": "CASA_MATRIZ"
  }
]
```

### SQL directo (ejemplo para MySQL/MariaDB)
```sql
INSERT INTO venta (id_cliente, total, sucursal) VALUES (1, 1850000, 'CASA_MATRIZ');
INSERT INTO venta_producto (id_venta, id_bicicleta, cantidad) VALUES (1, 1, 1);
```

## Notas
- Al registrar una venta, el stock se descuenta automáticamente.
- La boleta se genera y asocia a la venta.
- Puedes poblar ventas usando Postman o directamente en la base de datos.

## Pruebas rápidas en Postman
1. Registra una venta con productos y sucursal.
2. Consulta el historial de ventas.

---

> Para más detalles de integración, revisa el README general y el de inventario.
