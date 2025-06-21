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

## Ejecución
1. Configura la base de datos en `src/main/resources/application.properties`.
2. Compila y ejecuta:
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```
3. Prueba los endpoints desde Swagger, Postman o el frontend.

## Notas
- El registro desde frontend crea usuarios con rol CLIENTE.
- Para crear usuarios ADMIN, usa Swagger o inserta manualmente en la base de datos.
- El MVP1 se centra en la autenticación y comunicación con el microservicio de ventas.

---

¿Dudas? Revisa la documentación o contacta al equipo MasterBikes.
