<p align="right">
  <img src="../logo.svg" alt="MasterBikes Logo" width="120"/>
</p>

# Auth Service - MasterBikes

Microservicio de autenticación y gestión de usuarios para MasterBikes.

## Características principales
- Registro y login de usuarios con roles: `ADMIN`, `SUPERVISOR`, `TECNICO`, `VENDEDOR`, `CLIENTE`.
- Autenticación basada en JWT.
- CRUD de usuarios (solo para roles con permisos).
- Encriptación de contraseñas con BCrypt.
- Manejo de errores amigable y validaciones.
- Configuración CORS para integración con frontend.
- Documentación automática de la API con Swagger/OpenAPI.

## Endpoints principales

### Autenticación
- `POST /auth/login` — Login de usuario. Devuelve JWT y rol.
  - Request: `{ "email": "usuario@mail.com", "password": "12345678" }`
  - Response: `{ "token": "...", "rol": "CLIENTE", "mensaje": "Login exitoso." }`

### Registro
- `POST /api/usuarios/registro` — Registro de usuario (rol CLIENTE por defecto).
  - Request: `{ "nombre": "Nombre Apellido", "email": "usuario@mail.com", "password": "12345678", "telefono": "", "direccion": "", "fechaNacimiento": "" }`
  - Response: `{ "mensaje": "Usuario registrado correctamente.", "usuario": { ... } }`

### Usuarios (CRUD)
- `GET /api/usuarios` — Listar usuarios (solo para roles con permisos).
- `GET /api/usuarios/{id}` — Obtener usuario por ID.
- `PUT /api/usuarios/{id}` — Actualizar usuario.
- `DELETE /api/usuarios/{id}` — Desactivar usuario (borrado lógico).

## Roles soportados
- `ADMIN`: Gestión total (usuarios, roles, etc.).
- `SUPERVISOR`, `TECNICO`, `VENDEDOR`: Acceso según permisos definidos.
- `CLIENTE`: Registro y login básico.

## Seguridad
- JWT requerido para endpoints protegidos.
- CORS habilitado para frontend en `http://localhost:8080`.

## Documentación Swagger
- Accede a la documentación interactiva en: `http://localhost:8081/swagger-ui.html`

## Requisitos
- Java 17
- Spring Boot 3.5+
- MySQL (XAMPP recomendado)
- Maven


## 🚀 Pruebas en vivo con Swagger y HATEOAS

Sigue estos pasos para demostrar el funcionamiento real del Auth Service en una presentación o defensa:

### 1. Login de usuario
- Abre [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)
- Busca el endpoint `POST /auth/login`.
- Ejemplo de body:
  ```json
  {
    "email": "admin@masterbikes.com",
    "password": "12341234"
  }
  ```
- Haz clic en "Try it out" y ejecuta. Copia el token JWT de la respuesta.

### 2. Probar endpoints protegidos
- Usa el token JWT en el botón "Authorize" de Swagger UI.
- Prueba endpoints como `GET /api/usuarios` (requiere rol adecuado).

### 3. Ejemplo de request/response
- **Request:**
  ```json
  {
    "email": "admin@masterbikes.com",
    "password": "admin123"
  }
  ```
- **Response:**
  ```json
  {
    "token": "<JWT>",
    "rol": "ADMIN",
    "mensaje": "Login exitoso."
  }
  ```

### 4. Pruebas automáticas
- Ejecuta:
  ```sh
  mvn test
  ```
- Verifica que todos los tests pasen (`BUILD SUCCESS`).

---

> Todos los endpoints y ejemplos pueden ser probados en vivo desde Swagger UI o Postman.
3. Prueba los endpoints desde Swagger, Postman o el frontend.

## Poblamiento de usuarios

### JSON para Postman
```json
[
  {
    "nombre": "Admin",
    "email": "admin@masterbikes.com",
    "password": "admin123",
    "telefono": "111111111",
    "direccion": "Casa Matriz",
    "fechaNacimiento": "1980-01-01"
  },
  {
    "nombre": "Vendedor",
    "email": "vendedor@masterbikes.com",
    "password": "vendedor123",
    "telefono": "222222222",
    "direccion": "Sucursal 1",
    "fechaNacimiento": "1992-05-10"
  }
]
```

### SQL directo (ejemplo para MySQL/MariaDB)
> **Nota:** La contraseña debe estar encriptada con BCrypt. Puedes usar un generador online para obtener el hash.
```sql
INSERT INTO usuario (nombre, email, password, rol, activo, sucursal) VALUES
('Admin', 'admin@masterbikes.com', '$2a$10$HASHADMIN', 'ADMIN', true, 'CASA_MATRIZ'),
('Vendedor', 'vendedor@masterbikes.com', '$2a$10$HASHVENDEDOR', 'VENDEDOR', true, 'SUCURSAL_1');
```

## Pruebas rápidas en Postman
1. Registra un usuario con el endpoint de registro.
2. Haz login y copia el token.
3. Usa el token para listar usuarios o cambiar roles.

---

¿Dudas? Revisa la documentación o contacta al equipo MasterBikes.
