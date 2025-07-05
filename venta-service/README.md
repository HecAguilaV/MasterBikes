<p align="right">
  <img src="../logo.svg" alt="MasterBikes Logo" width="120"/>
</p>

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

## Pruebas automáticas y HATEOAS

### Pruebas unitarias y de integración
- Ejecuta en la raíz del microservicio:
  ```sh
  mvn test
  ```
- Verifica que todos los tests pasen (`BUILD SUCCESS`).
- Incluye pruebas unitarias de lógica de negocio y pruebas de endpoints (incluyendo HATEOAS) usando MockMvc y Mockito.

### Prueba de endpoint HATEOAS
- Endpoint: `GET /api/v1/ventas/{id}/hateoas`
- Devuelve la venta con enlaces navegables (`_links`) para self y listado de ventas.
- Ejemplo de respuesta:
  ```json
  {
    "id": 1,
    "fecha": "2025-07-05T12:00:00",
    ...
    "_links": {
      "self": { "href": "http://localhost:8085/api/v1/ventas/1/hateoas" },
      "ventas": { "href": "http://localhost:8085/api/v1/ventas" }
    }
  }
  ```
- Puedes probarlo desde Swagger UI (`/swagger-ui.html`) o Postman.

### Ejemplo de test automatizado (MockMvc)
```java
@Test
void getVentaHateoas_returnsHateoasLinks() throws Exception {
    Venta venta = Venta.builder()
            .id(1L)
            .fecha(LocalDateTime.now())
            .clienteId(100L)
            .sucursalId(10L)
            .vendedorId(5L)
            .total(50000.0)
            .medioPago("EFECTIVO")
            .detalles(Collections.emptyList())
            .factura(null)
            .build();

    Mockito.when(ventaService.findById(anyLong())).thenReturn(venta);

    mockMvc.perform(get("/api/v1/ventas/1/hateoas")
            .accept(MediaTypes.HAL_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$._links.self.href").exists())
            .andExpect(jsonPath("$._links.ventas.href").exists());
}
```

### Swagger UI
- Accede a la documentación y prueba los endpoints en:
  - [http://localhost:8085/swagger-ui.html](http://localhost:8085/swagger-ui.html)

---

> Para más detalles de integración, revisa el README general y el de inventario.
