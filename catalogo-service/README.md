<p align="right">
  <img src="../logo.svg" alt="MasterBikes Logo" width="120"/>
</p>

# Catálogo Service - MasterBikes

## Descripción
Microservicio encargado de la gestión del catálogo de bicicletas y componentes.

## Endpoints principales

### Bicicletas
- `GET /api/v1/catalogo/bicicletas`  
  Lista todas las bicicletas disponibles.
- `POST /api/v1/catalogo/bicicletas`  
  Agrega una nueva bicicleta al catálogo.
  
  **Body ejemplo:**
  ```json
  {
    "name": "Bicicleta Orion Futurista",
    "brand": "MasterBikes",
    "type": "Montaña",
    "size": "M",
    "price": 1599990,
    "image": "images/orion.jpg",
    "description": "Bicicleta futurista con tecnología avanzada, ideal para ciclistas competitivos.",
    "rating": 4.0
  }
  ```

### Componentes
- `GET /api/v1/catalogo/componentes`  
  Lista todos los componentes disponibles.
- `POST /api/v1/catalogo/componentes`  
  Agrega un nuevo componente.
  
  **Body ejemplo:**
  ```json
  {
    "tipo": "Cuadro",
    "marca": "Trek",
    "modelo": "Alpha Gold",
    "precio": 150000
  }
  ```

## 🚀 Pruebas en vivo con Swagger y HATEOAS

Sigue estos pasos para demostrar el funcionamiento real del Catálogo Service en una presentación o defensa:

### 1. Consultar catálogo de bicicletas
- Abre [http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html)
- Prueba el endpoint `GET /api/v1/catalogo/bicicletas` para ver el catálogo real.

### 2. Agregar una bicicleta
- Usa `POST /api/v1/catalogo/bicicletas`.
- Ejemplo de body:
  ```json
  {
    "name": "Bicicleta Orion Futurista",
    "brand": "MasterBikes",
    "type": "Montaña",
    "size": "M",
    "price": 1599990,
    "image": "images/orion.jpg",
    "description": "Bicicleta futurista con tecnología avanzada, ideal para ciclistas competitivos.",
    "rating": 4.0
  }
  ```

### 3. Pruebas automáticas
- Ejecuta:
  ```sh
  mvn test
  ```
- Verifica que todos los tests pasen (`BUILD SUCCESS`).

---

> Todos los endpoints y ejemplos pueden ser probados en vivo desde Swagger UI o Postman.

## Poblamiento de catálogo

### JSON para bicicletas (Postman)
```json
[
  {
    "name": "Bicicleta Orion Futurista",
    "brand": "MasterBikes",
    "type": "Montaña",
    "size": "M",
    "price": 1599990,
    "image": "images/orion.jpg",
    "description": "Bicicleta futurista con tecnología avanzada, ideal para ciclistas competitivos.",
    "rating": 4.0
  },
  ...
]
```

### JSON para componentes (Postman)
```json
[
  {
    "tipo": "Cuadro",
    "marca": "Trek",
    "modelo": "Alpha Gold",
    "precio": 150000
  },
  ...
]
```

### SQL directo (ejemplo para MySQL/MariaDB)
```sql
INSERT INTO bicicleta (name, brand, type, size, price, image, description, rating) VALUES
('Bicicleta Orion Futurista', 'MasterBikes', 'Montaña', 'M', 1599990, 'images/orion.jpg', 'Bicicleta futurista con tecnología avanzada, ideal para ciclistas competitivos.', 4.0);

INSERT INTO componente (tipo, marca, modelo, precio) VALUES
('Cuadro', 'Trek', 'Alpha Gold', 150000);
```

## Notas
- Las imágenes deben existir en la carpeta `/frontend/images/` y la ruta debe coincidir.
- Puedes poblar el catálogo usando Postman o directamente en la base de datos.

## Pruebas rápidas en Postman
1. Agrega bicicletas y componentes usando los endpoints POST.
2. Consulta el catálogo con los endpoints GET.

---

> Para más detalles de integración, revisa el README general y el del frontend.
