# 🚴‍♂️ MasterBikes — Guía de Colaboración y Buenas Prácticas

¡Bienvenido/a al proyecto MasterBikes! 🚀

Aquí encontrarás las reglas de oro para que el trabajo en equipo sea ágil, divertido y profesional. ¡Tu aporte suma! 💡

---

## 📁 Estructura del Repositorio

```
masterbikes/
├── auth-service/           # 🔐 Microservicio de autenticación y usuarios
├── catalogo-service/       # 📦 Microservicio de catálogo de productos
├── inventario-service/     # 🏷️ Microservicio de inventario
├── sucursal-service/       # 🏢 Microservicio de sucursales
├── frontend/               # 💻 Frontend web (HTML, CSS, JS)
└── ...                     # 📄 Documentación, scripts, otros recursos
```

---

## 🛠️ Convenciones Generales

- **Backend:** Java 17, Spring Boot 3.5+, Maven
- **Frontend:** HTML, CSS, JavaScript puro
- **Base de datos:** MySQL (XAMPP recomendado)
- **API Docs:** Swagger/OpenAPI

---

## ✍️ Convenciones de Nombres

- Clases Java: `CamelCase` (ej: `UsuarioController`)
- Paquetes Java: `minusculas.separadas.por.puntos`
- Variables y métodos: `camelCase`
- Ramas git: `feature/nombre-corto`, `fix/descripcion`, `hotfix/descripcion`
- Commits: en español, en presente, claros y concisos

> Ejemplo de commit: `fix: corrige validación de email en registro`

---

## 🌱 Flujo de Trabajo (Git)

1. **Crea una rama para cada tarea:**
   ```sh
   git checkout -b feature/nueva-funcionalidad
   ```
2. **Haz commits frecuentes y descriptivos.**
3. **Antes de hacer push, sincroniza tu rama:**
   ```sh
   git pull origin main
   ```
4. **Haz push de tu rama:**
   ```sh
   git push origin feature/nueva-funcionalidad
   ```
5. **Crea un Pull Request (PR) y solicita revisión.**
6. **No hagas push directo a `main` salvo emergencias.**

> ¡Recuerda! Un buen PR facilita la revisión y mejora la calidad del código de todos. 🙌

---

## 💡 Buenas Prácticas de Desarrollo

- 📚 **Documenta tu código** con comentarios claros y útiles.
- 🔄 **Mantén la compatibilidad** entre microservicios (contratos de API, DTOs, etc.).
- 🔒 **Usa variables de entorno** para credenciales y configuraciones sensibles.
- 🚫 **No subas archivos sensibles** (`application.properties` con contraseñas, etc.).
- 🧪 **Agrega pruebas unitarias/integración** cuando sea posible.
- 📝 **Actualiza la documentación** (`README.md`, Swagger, etc.) tras cambios relevantes.

---

## 🔗 Integración y Pruebas

- 🏠 **Prueba localmente** cada microservicio antes de integrarlo.
- 🧑‍💻 **Verifica endpoints** desde Swagger, Postman y frontend.
- 🌐 **Asegura CORS y JWT** para la comunicación frontend-backend.
- 🧩 **Mantén consistencia en los roles y permisos** entre servicios.

---

## 🚀 Despliegue

- **Usa Maven** para compilar y ejecutar microservicios:
  ```sh
  mvn clean install
  mvn spring-boot:run
  ```
- **Configura la base de datos** en cada microservicio (`src/main/resources/application.properties`).
- **El frontend puede abrirse directamente en navegador** (`frontend/index.html`).

---

## 📚 Recursos Útiles

- [Guía de commits convencionales](https://www.conventionalcommits.org/es/v1.0.0/)
- [Documentación oficial Spring Boot](https://spring.io/projects/spring-boot)
- [Swagger/OpenAPI](https://swagger.io/tools/swagger-ui/)

---

## 🤝 Contacto y Dudas

- Usa los issues de GitHub para reportar bugs o sugerencias.
- Contacta al equipo MasterBikes para soporte o dudas mayores.

---

> ¡Gracias por contribuir a MasterBikes! Tu trabajo hace la diferencia. 🚴‍♀️✨
