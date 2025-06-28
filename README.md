# 🚴‍♂️ MasterBikes - Plataforma de Comercio Electrónico para Bicicletas

MasterBikes es una aplicación web completa para la venta, arriendo y servicio técnico de bicicletas, desarrollada con arquitectura de microservicios.

## 📋 Tabla de Contenidos
- [Arquitectura del Sistema](#arquitectura-del-sistema)
- [Stack Tecnológico](#stack-tecnológico)
- [Endpoints Principales](#endpoints-principales)
- [Pruebas](#pruebas)
- [Problemas de Comunicación](#problemas-de-comunicación)
- [Diagramas](#diagramas)
- [Configuración y Despliegue](#configuración-y-despliegue)

## 🏗️ Arquitectura del Sistema

El proyecto está organizado en microservicios independientes:
masterbikes/
├── servicio de autenticación/
├── catalogo-ser
├── inventario-servicio/
├── sucursal-servicio/ # 🏢 Microser
├── venta-ser
├── interfaz/
└── ...


## 🛠️ Stack Tecnológico

### Backend
- **Lenguaje**: Java 17
- **Framework**: Spring Boot 3.5+
- **Gestión de dependencias**: Maven
- **Seguridad**: JWT + BCrypt
- **Documentación**: Swagger/OpenAPI
- **Comunicación**: RestTemplate

### Frontend
- **Tecnologías**: HTML5, CSS3, JavaScript ES6+
- **Framework CSS**: Bootstrap 5.3.3
- **Iconografía**: FontAwesome 6.5.0
- **Funcionalidades**: EmailJS, LocalStorage

### Base de Datos
- **Motor**: MySQL
- **Herramienta recomendada**: XAMPP
- **ORM**: Spring Data JPA

## 🔗 Endpoints Principales

### Auth Service (Puerto 8081)
- `POST /auth/login` - Autenticación de usuario
- `POST /api/usuarios/registro` - Registro público
- `GET /api/usuarios` - Listar usuarios (con permisos)
- `GET /api/usuarios/{id}` - Obtener usuario por ID
- `PUT /api/usuarios/{id}` - Actualizar usuario
- `DELETE /api/usuarios/{id}` - Desactivar usuario

### Catalogo Service (Puerto 8082)
- `GET /api/v1/catalogo/bicicletas` - Listar bicicletas
- `GET /api/v1/catalogo/accesorios` - Listar accesorios
- `GET /api/v1/catalogo/componentes` - Listar componentes
- `POST /api/v1/catalogo/{tipo}` - Crear productos

### Inventario Service (Puerto 8084)
- `GET /api/v1/inventarios` - Listar inventario
- `POST /api/v1/movimientosinventario` - Registrar movimientos
- `GET /api/v1/reportesucursal/{id}` - Reportes por sucursal

### Sucursal Service (Puerto 8083)
- `GET /api/v1/sucursales` - Listar sucursales
- `GET /api/v1/empleados` - Listar empleados

### Venta Service (Puerto 8085)
- `GET /api/v1/ventas` - Listar ventas
- `GET /api/v1/facturas` - Listar facturas

## 🧪 Pruebas

### Estrategia de Testing
- **Framework**: JUnit 5 + Mockito
- **Cobertura**: Tests unitarios en auth-service
- **Patrón**: `@SpringBootTest` para tests de integración
- **Mocking**: Repositorios y servicios externos

### Ejecución de Pruebas
```bash  
mvn test                    # Ejecutar todas las pruebas  
mvn test -Dtest=ClassName   # Ejecutar prueba específica  
⚠️ Problemas de Comunicación Actuales
Configuración CORS
Todos los servicios t
Pe
Configurado en cada microservicio
Comunicación Inter-Servicios
RestTemplate : Comunicac
URL codificadas : localhost con puertos específicos
Manejo de errores : Básico, puede mejorarse
Tiempo de espera : No configurado específicamente
Problemas identificados
URL codificadas en lugar de descubrimiento de servicios
Falta de disyuntorespara resiliencia
No hay equilibrio de carga entre instancias
Registro distribuido no implementado
📊 Diagramas
Arquitectura de Microservicios
Base de Datos

Microservicios Backend

Frontend

Frontend Web
HTML, CSS, JS

Auth Service
:8081

Catalogo Service
:8082

Inventario Service
:8084

Sucursal Service
:8083

Venta Service
:8085

MySQL Database

Flujo de Proceso de Venta
Database
Inventario Service
Catalogo Service
Venta Service
Frontend
Database
Inventario Service
Catalogo Service
Venta Service
Frontend
POST /api/v1/ventas
GET precio producto
Precio unitario
POST movimiento inventario
Confirmación
Guardar venta + factura
Venta guardada
Venta completada
Stack Tecnológico
Documentation & Testing

Swagger/OpenAPI

JUnit Tests

Mockito

Database Stack

MySQL

XAMPP

Spring Data JPA

Backend Stack

Java 17

Spring Boot 3.5+

Maven

JWT Security

BCrypt

Frontend Stack

HTML5

CSS3

JavaScript ES6+

Bootstrap 5.3.3

FontAwesome 6.5.0

⚙️ Configuración y Despliegue
Requisitos previos
Java 17 o superior
MySQL 8.0+
Maven 3.6+
XAMPP (recomendado para desarrollo)
Instalación
# Clonar repositorio  [header-2](#header-2)
git clone https://github.com/HecAguilaV/MasterBikes.git  
cd MasterBikes  
  
# Configurar base de datos  [header-3](#header-3)
# Iniciar XAMPP y crear base de datos 'masterbikes'  [header-4](#header-4)
  
# Compilar y ejecutar cada microservicio  [header-5](#header-5)
cd auth-service  
mvn clean install  
mvn spring-boot:run  
  
# Repetir para cada servicio en puertos específicos  [header-6](#header-6)
Puertos de Servicios
Servicio de autenticación : 8081
Servicio de Catálogo : 8082
Servicio Sucursal : 8083
Servicio de Inventario : 8084
Venta Servicio : 8085
Frontend : Servidor web estático
Documentación API
Servicio de autenticación:http://localhost:8081/swagger-ui.html
Otros servicios:http://localhost:{puerto}/swagger-ui.html
🤝 Desarrollo y Colaboración
Convenciones de Código
Clases Java : CamelCase(ej UsuarioController:)
Paquetes :minusculas.separadas.por.puntos
Variables y métodos :camelCase
Compromisos : En español, presente, descriptivos
Flujo de trabajo
# Crear rama feature  [header-7](#header-7)
git checkout -b feature/nueva-funcionalidad  
  
# Realizar cambios y commits  [header-8](#header-8)
git add .  
git commit -m "feat: agrega nueva funcionalidad"  
  
# Push y crear PR  [header-9](#header-9)
git push origin feature/nueva-funcionalidad
📝 Servicios de Negocio
Arriendo de bicicletas
Planes diarios, semanales y mensuales
Gestión de disponibilidad por sucursal
Sistema de reservas
Venta de productos
Catálogo de bicicletas y accesorios
Gestión de inventario en tiempo real
Facturación automática
Servicio Técnico
Taller certificado Shimano
Mantenimiento preventivo
Reparaciones especializadas
🔒 Seguridad
Autenticación
Tokens JWT para sesiones
Roles: ADMIN, SUPERVISOR, TECNICO, VENDEDOR, CLIENTE
Encriptación BCrypt para contraseñas
Autorización
Puntos finales protegidos por rol
Validación de permisos en cada servicio
CORS configurado para la interfaz
📈 Próximas mejoras
Descubrimiento de servicios de implementación (Eureka)
Agregar disyuntores (Hystrix/Resilience4j)
Configurar el registro distribuido (ELK Stack)
Monitoreo de implementaciones (Micrometer + Prometheus)
Agregar pruebas de integración completas
Servicios Dockerizar
Implementar canalización de CI/CD
© 2024 MasterBikes. Todos los derechos reservados.