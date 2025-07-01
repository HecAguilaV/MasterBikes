# Frontend - MasterBikes

## Descripción
Interfaz web para clientes, vendedores, técnicos, supervisores y admins. Permite navegar el catálogo, personalizar bicicletas, comprar, gestionar usuarios y más.

## Estructura principal
- `/index.html` — Página de inicio
- `/pages/catalogo.html` — Catálogo de bicicletas
- `/pages/personalizacion.html` — Personalización de bicicletas
- `/pages/cart.html` — Carrito de compras
- `/pages/pago.html` — Pago y confirmación
- `/pages/admin.html` — Administración de usuarios (solo admins)
- `/js/` — Scripts de negocio y utilidades
- `/images/` — Imágenes de productos

## Cómo levantar el frontend
- Puedes usar el `server.js` incluido (Node.js) para desarrollo local:
  ```sh
  cd frontend
  node pages/server.js
  ```
- O servirlo con Nginx/Apache en producción.

## Pruebas rápidas
1. Accede a `http://localhost:8080/pages/catalogo.html` para ver el catálogo.
2. Regístrate, inicia sesión y prueba el flujo de compra.
3. Si eres admin, accede a `/pages/admin.html` para gestionar usuarios.

## Notas
- El frontend consume los endpoints del API Gateway (`http://localhost:9000`).
- El menú y las páginas pueden adaptarse según el rol del usuario (ver scripts JS).
- Las imágenes deben coincidir con las rutas usadas en la base de datos.

## Scripts útiles
- `js/populateCatalog.js`: Script para poblar el catálogo desde el frontend usando Postman o consola.
- `js/personalizacion.js`: Datos de componentes para personalización.

---

> Para detalles de endpoints y pruebas, revisa los README de cada microservicio y el general.
