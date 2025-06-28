# 🚴‍♂️ MasterBikes - MVP Microservicios

## Resumen General
MasterBikes es una plataforma de e-commerce para bicicletas, accesorios y servicios, basada en microservicios. El objetivo MVP es permitir a un usuario cliente autenticarse, ver el catálogo real, agregar productos al carrito y realizar una compra registrada en el backend.

---

## 🏗️ Arquitectura General

```mermaid
architecture-beta
    group frontend(cloud)[Frontend Web]
    group backend(cloud)[Backend Microservicios]
    service auth(server)[Auth Service :8081] in backend
    service catalog(server)[Catálogo :8082] in backend
    service venta(server)[Venta :8085] in backend
    service inventario(server)[Inventario :8084] in backend
    service sucursal(server)[Sucursal :8083] in backend
    service db(database)[MySQL] in backend

    frontend:R --> L:auth
    frontend:R --> L:catalog
    frontend:R --> L:venta
    catalog:B -- T:db
    venta:B -- T:db
    inventario:B -- T:db
    sucursal:B -- T:db
```

---

## 🛠️ Stack Tecnológico
- **Backend:** Java 17, Spring Boot 3.5+, Spring Data JPA, JWT, BCrypt, Maven, MySQL
- **Frontend:** HTML5, CSS3, JavaScript ES6+, Bootstrap 5, EmailJS
- **Infraestructura:** XAMPP (dev), Postman (pruebas), Localhost

---

## 🔗 Endpoints Principales

### Auth Service (8081)
- `POST /auth/login` - Login de usuario `{ email, password }`
- `POST /api/usuarios/registro` - Registro de usuario

### Catálogo Service (8082)
- `GET /api/v1/catalogo/bicicletas` - Listar bicicletas
- `POST /api/v1/catalogo/bicicletas` - Crear bicicleta (requiere BicicletaDTO)

### Venta Service (8085)
- `POST /api/v1/ventas` - Registrar venta (requiere objeto Venta)
- `GET /api/v1/ventas` - Listar ventas

---

## � Problemas y Desafíos Actuales
- **Desacople DTOs:** El backend espera BicicletaDTO (IDs de componentes), pero el frontend y scripts intentan poblar con productos simples (name, brand, etc.).
- **Poblamiento:** No se puede poblar el catálogo con productos simples sin modificar el backend o conocer los IDs de componentes.
- **Compra:** El frontend simula la compra (EmailJS) pero no realiza POST real a `/api/v1/ventas`.
- **Autenticación:** El login funciona y guarda el token, pero el flujo de compra no lo utiliza para registrar ventas reales.
- **Integración:** Falta integración real de carrito → compra → registro en backend.
- **Documentación:** Falta documentación clara de los DTOs requeridos para poblar y comprar.

---

## 🚦 Recomendaciones para el MVP
1. **Poblar catálogo:** Usar el formato BicicletaDTO o adaptar el backend para aceptar productos simples.
2. **Login:** Usar `/auth/login` y guardar el token JWT.
3. **Compra real:** Modificar el frontend para enviar el carrito como objeto Venta a `/api/v1/ventas` usando el token.
4. **Verificación:** Usar Postman para poblar, loguear y comprar, asegurando que los endpoints funcionen de extremo a extremo.
5. **Documentar DTOs:** Agregar ejemplos de JSON válidos para poblar y comprar en el README.

---

## 📦 Ejemplo de JSON para poblar Bicicleta (BicicletaDTO)
```json
{
  "idCliente": "1",
  "tallaUsuario": "M",
  "idMarco": 1,
  "idRueda": 2,
  "idFreno": 3,
  "idManubrio": 4,
  "idSillin": 5,
  "esPredefinida": true,
  "modelo": "MTB Demo 29"
}
```

## 🛒 Ejemplo de JSON para registrar una venta
```json
{
  "idCliente": 1,
  "productos": [
    { "idBicicleta": 10, "cantidad": 1 }
  ],
  "total": 1850000
}
```

---

## 📈 Estado actual
- El login y la obtención de token funcionan.
- El poblamiento y la compra requieren ajuste de DTOs y/o endpoints.
- El frontend debe adaptarse para trabajar con los datos y flujos reales del backend.

---

© 2025 MasterBikes. Todos los derechos reservados.