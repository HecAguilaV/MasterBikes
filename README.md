# 🚴‍♂️ MasterBikes - MVP Microservicios

## Resumen General
MasterBikes es una plataforma de e-commerce para bicicletas, accesorios y servicios, basada en microservicios. El objetivo MVP es permitir a un usuario cliente autenticarse, ver el catálogo real, agregar productos al carrito y realizar una compra registrada en el backend.

---


## 🏗️ Arquitectura General

```mermaid
flowchart LR
    subgraph Frontend
        A[Web (localhost:63342)]
    end
    subgraph Gateway
        G[API Gateway (localhost:9000)\nCORS centralizado]
    end
    subgraph Backend
        B1[Auth Service :8081]
        B2[Catálogo :8082]
        B3[Venta :8085]
        B4[Inventario :8084]
        B5[Sucursal :8083]
        DB[(MariaDB/XAMPP)]
    end
    A-->|CORS|G
    G-->|Proxy|B1
    G-->|Proxy|B2
    G-->|Proxy|B3
    G-->|Proxy|B4
    G-->|Proxy|B5
    B1-->|JPA|DB
    B2-->|JPA|DB
    B3-->|JPA|DB
    B4-->|JPA|DB
    B5-->|JPA|DB
```

---


## 🛠️ Stack Tecnológico
- **Backend:** Java 17, Spring Boot 3.5+, Spring Data JPA, JWT, BCrypt, Maven, MySQL/MariaDB
- **Frontend:** HTML5, CSS3, JavaScript ES6+, Bootstrap 5, EmailJS
- **Infraestructura:** XAMPP (dev, usa MariaDB), Postman (pruebas), Localhost

> ⚠️ **Nota:** Si usas XAMPP, tu base es MariaDB aunque el driver de MySQL funcione. Puedes ignorar la advertencia de dialecto o cambiar a `MariaDBDialect` en `application.properties`.

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


## 🐞 Problemas y Desafíos Actuales
- **CORS:** Ahora solo el API Gateway maneja CORS. No debe haber configuración CORS en los microservicios.
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
6. **Reiniciar servicios:** Tras cada cambio en el backend, reinicia los microservicios y el API Gateway para aplicar la configuración.

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
- CORS centralizado en el API Gateway, sin configuración CORS en microservicios.


© 2025 MasterBikes. Todos los derechos reservados.