# 🚴‍♂️ MasterBikes - MVP Microservicios

## Resumen General
MasterBikes es una plataforma de e-commerce para bicicletas, accesorios y servicios, basada en microservicios. El objetivo MVP es permitir a un usuario cliente autenticarse, ver el catálogo real, agregar productos al carrito y realizar una compra registrada en el backend.

---


## 🏗️ Arquitectura General

| Componente         | Puerto   | Descripción                        |
|--------------------|----------|------------------------------------|
| Frontend           | 8080     | Interfaz web (HTML/JS/CSS)         |
| API Gateway        | 8080     | Centraliza rutas y CORS            |
| Auth Service       | 8081     | Usuarios y autenticación           |
| Catálogo Service   | 8082     | Bicicletas y componentes           |
| Venta Service      | 8085     | Registro de ventas y boletas       |
| Inventario Service | 8084     | Stock por sucursal                 |
| Sucursal Service   | 8083     | Gestión de sucursales              |
| Base de datos      | 3306     | MariaDB/MySQL (XAMPP)              |

| API Gateway        | 8080     | Centraliza rutas y CORS            |
| Auth Service       | 8081     | Usuarios y autenticación           |
| Catálogo Service   | 8082     | Bicicletas y componentes           |
| Sucursal Service   | 8083     | Gestión de sucursales              |
| Inventario Service | 8084     | Stock por sucursal                 |
| Venta Service      | 8085     | Registro de ventas y boletas       |
| Frontend           | 8090     | Interfaz web (HTML/JS/CSS)         |


### 🖼️ Diagrama de Arquitectura

<p align="center">
  <img src="./DiagramaArquitectura.png" alt="Diagrama de Arquitectura MasterBikes" width="700"/>
</p>

<details>
<summary><strong>🗒️ Leyenda y explicación</strong> (clic para expandir)</summary>

**Leyenda:**

- <span style="color:#388e3c;">Flechas verdes</span>:
  - <strong>CORS/HTTP</strong>: Comunicación entre el frontend y el API Gateway.
  - <strong>Proxy</strong>: El API Gateway enruta/redirecciona las peticiones a los microservicios.
  - <strong>JPA</strong>: Acceso a la base de datos usando Java Persistence API (Spring Data JPA).
- <strong>Colores de los rectángulos</strong>:
  - Azul: Servicios y gateway (backend y frontend)
  - Amarillo: Base de datos

**Explicación:**

El diagrama muestra la arquitectura de MasterBikes basada en microservicios. El usuario interactúa con el <strong>Frontend</strong>, que se comunica vía HTTP con el <strong>API Gateway</strong>. El gateway centraliza la gestión de rutas y CORS, y actúa como proxy hacia los microservicios: autenticación, catálogo, ventas, inventario y sucursales. Todos los servicios acceden a la base de datos relacional (MariaDB/MySQL) usando JPA. Este diseño permite escalabilidad, seguridad y separación clara de responsabilidades.

</details>


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


## 🚢 Cómo levantar el entorno con Docker (para todo el equipo)

1. **Instala Docker Desktop**
   - Descarga e instala desde https://www.docker.com/products/docker-desktop/

2. **Compila los microservicios**
   - En cada carpeta de microservicio (por ejemplo, `api-gateway`, `auth-service`, etc.), ejecuta:
     ```
     mvn clean package
     ```
     Esto generará el archivo `.jar` en la carpeta `target`.

3. **Levanta todo el sistema**
   - Desde la raíz del proyecto (donde está `docker-compose.yml`), ejecuta:
     ```
     docker-compose up --build
     ```
   - Esto construirá las imágenes y levantará todos los servicios (backend, frontend y base de datos).

4. **Accede a la app**
   - API Gateway (backend): [http://localhost:8080](http://localhost:8080)
   - Frontend (estático): [http://localhost:8090](http://localhost:8090)
   - Cada microservicio expone su puerto (ver tabla de arquitectura).

5. **Detener todo**
   - Para detener los servicios, presiona `Ctrl+C` en la terminal o ejecuta:
     ```
     docker-compose down
     ```

> **¡Listo!** Así todos tendrán el mismo entorno, sin importar el sistema operativo o configuraciones locales.

---

## 🟢 Guía "con peras y manzanas" para levantar MasterBikes con Docker

### 1. Instala Docker Desktop
Descarga e instala Docker Desktop desde [docker.com/products/docker-desktop](https://www.docker.com/products/docker-desktop). Solo tienes que hacerlo una vez.

### 2. Compila los microservicios
Abre una terminal (puede ser CMD, PowerShell o la terminal de VSCode) y navega a cada carpeta de microservicio (`api-gateway`, `auth-service`, etc.). En cada una ejecuta:

```sh
mvn clean package
```

Esto genera un archivo `.jar` en la carpeta `target` de cada microservicio. ¡Hazlo en todos!

### 3. Levanta todo el sistema
Vuelve a la raíz del proyecto (donde está el archivo `docker-compose.yml`). Ejecuta:

```sh
docker-compose up --build
```

Esto descargará lo necesario, construirá las imágenes y levantará todos los servicios automáticamente. La primera vez puede demorar varios minutos.

### 4. Prueba la aplicación
- Abre tu navegador y entra a:
  - API Gateway (backend): [http://localhost:8080](http://localhost:8080)
  - Frontend (estático): [http://localhost:8090](http://localhost:8090)
- Los microservicios estarán disponibles en sus puertos (ver tabla de arquitectura).

### 5. Detén todo
En la terminal donde ejecutaste Docker, presiona `Ctrl+C` para detener los servicios. Si quieres limpiar todo, ejecuta:

```sh
docker-compose down
```

---

### Preguntas frecuentes

**¿Tengo que instalar Java, Maven o MySQL en mi PC?**
> No, solo necesitas Docker Desktop. Todo lo demás lo hace Docker.

**¿Qué hago si me da error de puertos ocupados?**
> Asegúrate de que XAMPP, MySQL local u otros programas no estén usando los mismos puertos (3306, 8080, etc.).

**¿Puedo programar y probar cambios?**
> Sí. Haz cambios en el código, vuelve a compilar el microservicio con `mvn clean package` y reinicia Docker (`docker-compose up --build`).

**¿Y si nunca he usado Docker?**
> Solo sigue estos pasos. No necesitas saber nada más para levantar el sistema.

---