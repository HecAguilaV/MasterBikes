# API Gateway - MasterBikes

## Descripción
El API Gateway centraliza el acceso a todos los microservicios, maneja CORS, rutas y seguridad.

## Funcionalidades principales
- Redirección de rutas a los microservicios correspondientes.
- Centralización de CORS (solo aquí debe configurarse).
- Seguridad básica (puedes agregar filtros de autenticación si lo requieres).

## Configuración
- El archivo principal de configuración es `src/main/resources/application.yml`.
- El puerto por defecto es `8080`.

## Ejemplo de rutas
- `/auth/*` → auth-service
- `/api/usuarios/*` → auth-service
- `/api/v1/catalogo/*` → catalogo-service
- `/inventario/*` → inventario-service
- `/sucursal/*` → sucursal-service
- `/api/v1/ventas/*` → venta-service

## Notas
- No debe haber configuración CORS en los microservicios, solo aquí.
- Si cambias los puertos de los microservicios, actualiza el `application.yml`.

## Pruebas rápidas
- Accede a cualquier endpoint a través de `http://localhost:8080/`.
- Verifica que las respuestas y CORS funcionen correctamente.

---

> Para detalles de endpoints y pruebas, revisa los README de cada microservicio.
