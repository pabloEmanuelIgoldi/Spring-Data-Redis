#  **Spring Data + Redis(Cache)**

# Índice
### Definición del proyecto
### Arquitectura del Proyecto
### Tecnologías y Dependencias
### Spring Cache
### Redis
### Ejecución del Proyecto
### Documentacion oficial
### Índice de proyectos Spring


# Definición del proyecto

Este proyecto demuestra cómo integrar **Spring Boot** con **Redis** como servidor de base de datos en memoria (caché) para optimizar el rendimiento de una aplicación 
mediante el patrón **Cache-Aside**.

## Objetivo

Implementar una capa de caché utilizando Redis para reducir la latencia en las operaciones de acceso a datos, evitando consultas repetitivas al motor de base de datos relacional y mejorando el rendimiento general del sistema.

# Arquitectura del Proyecto

El proyecto está dividido en las siguientes capas:

- **Controlador (`Controller`)**  
  Maneja las peticiones HTTP. Solo opera con objetos DTO.

- **Servicio (`Service`)**  
  Actúa como intermediario entre el controlador y el DAO. Mapea entidades (`Entities`) a DTOs y viceversa, sin acoplarse a la implementación del DAO.

- **DAO (`Data Access Object`)**  
  Capa encargada del acceso y gestión de los datos. Realiza operaciones CRUD sobre la base de datos y maneja la lógica de caché en Redis. Solo trabaja con entidades que reflejan el modelo de datos.

---

# Tecnologías y Dependencias

La aplicación está construida con:

- **Java 17**
- **Spring Boot 3.3.11**
- **PostgreSQL**
- **Redis**

### Dependencias principales:

| Dependencia                             | Descripción                                      |
|----------------------------------------|--------------------------------------------------|
| `spring-boot-starter-web`              | Construcción de aplicaciones web                |
| `spring-boot-starter-validation`       | Validación de datos                             |
| `spring-boot-starter-cache`            | Habilita sistema de caché en Spring             |
| `spring-boot-starter-data-redis`       | Integración con Redis                           |
| `spring-boot-starter-data-jpa`         | Acceso a bases de datos relacionales (JPA)      |
| `lombok`                                | Reducción de código repetitivo con anotaciones  |
| `springdoc-openapi-starter-webmvc-ui` | Documentación y pruebas de API (Swagger)        |
| `postgresql`                            | Driver del motor de base de datos utilizado     |

---

#  Spring Cache

Spring Cache provee una capa de abstracción sobre múltiples motores de caché como Redis, EhCache, Caffeine, entre otros.

### Herramientas y anotaciones clave:

- `@EnableCaching`: Habilita el uso de caché en la aplicación.
- `@Cacheable`: Guarda el resultado de un método en caché.
- `@CacheEvict`: Elimina entradas específicas de la caché.
- `@CachePut`: Actualiza la caché tras ejecutar el método.
- `@Caching`: Agrupa múltiples operaciones de caché.
- `@CacheConfig`: Define configuración común para métodos de caché.
- `RedisTemplate`: Proporciona operaciones de bajo nivel con Redis.
- `Repositorios Redis`: Alternativa al estilo JPA para Redis.

---

#  Redis (Remote Dictionary Server)

**Redis** es una base de datos NoSQL basada en memoria RAM, capaz de entregar respuestas a altísima velocidad, ideal para implementar sistemas de caché en tiempo real.

---

## Patrón Cache-Aside

Este proyecto implementa el patrón **Cache-Aside**, uno de los más utilizados en sistemas distribuidos.

### Flujo típico:

1. El usuario realiza una petición (`getAllProductos`, `getProductoById`, etc.).
2. La aplicación verifica si el dato existe en Redis.
3. Si no lo encuentra:
   - Consulta a la base de datos.
   - Guarda el resultado en Redis.
4. En próximas consultas, si los datos siguen en caché (TTL no expirado), se devuelven desde Redis.
5. Si el dato ha expirado o fue eliminado, el flujo se repite.

### Ventajas:
- Sencillo de implementar.
- Reduce la carga en la base de datos.
- Aumenta la velocidad de respuesta en accesos repetidos.

###  Desventaja:
- La primera petición siempre accede a la base de datos (latencia inicial).

---
# Ejecución del Proyecto

### Requisitos:

- Java 17
- Docker (para Redis y PostgreSQL, opcional)
- IDE o herramienta de compilación (IntelliJ, Maven, etc.)

### Pasos:

1. Clona el repositorio.
2. Configura las propiedades en `application.properties`.
3. Levanta Redis y PostgreSQL (puedes usar Docker).
4. Levanta la aplicación.
5. Usa Swagger para probar la aplicación.

### Servicios: PostgreSQL y Redis

Puedes levantar ambos servicios con un único archivo `docker-compose.yml`:

```yaml
version: '3.8'

services:
  redis:
    image: redis:latest
    container_name: redis-productos
    ports:
      - "6379:6379"
    volumes:
      - redis-data:/data
    restart: always
    command: redis-server --appendonly yes
    networks:
      - backend-network

  postgres:
    image: postgres:latest
    container_name: postgres-productos
    environment:
      POSTGRES_DB: postgres_1_db
      POSTGRES_USER: user_postgres
      POSTGRES_PASSWORD: pass_postgres
    ports:
      - "5432:5432"
    volumes:
      - postgres-data:/var/lib/postgresql/data
    restart: always
    networks:
      - backend-network

networks:
  backend-network:
    driver: bridge

volumes:
  redis-data:
  postgres-data:
```


### Configuraciones del application.properties:   
Dentro del applicacion.properties tenemos las siguientes configuraciones relacionadas a Redis.

Habilita Redis como proveedor de caché:

**spring.cache.type=redis**

Define Redis local en el puerto por defecto (6379):

**spring.data.redis.host=localhost**

**spring.data.redis.port=6379**

TTL de 1 hora para las entradas en caché (en milisegundos):

**spring.cache.redis.time-to-live=3600000**

No almacena valores null en caché:

**spring.cache.redis.cache-null-values=false**

Habilita estadísticas del uso de caché (aciertos, fallos, etc.):

**spring.cache.redis.enable-statistics=true**

Añade prefijos a las claves para evitar colisiones entre caches:

**spring.cache.redis.use-key-prefix=true**

### SWAGGER:

Para probar la API se puede ingresar a:

http://localhost:8080/api-redis/swagger-ui/index.html

http://localhost:8080/api-redis-docs


---
#  **Documentacion oficial**

  https://spring.io/projects/spring-data-redis
  
  https://spring.io/guides/gs/spring-data-reactive-redis
  
  https://redis.io/solutions/caching/
  
  https://redis.io/docs/latest/
  
---
#  **Índice de proyectos Spring**
##  **Proyectos Spring Boot**
- [Response Uniforme](https://github.com/pabloEmanuelIgoldi/Spring-Boot-Response-Wrapper)
- [LogBack](https://github.com/pabloEmanuelIgoldi/Spring-Boot-Logback)
- [Profile](https://github.com/pabloEmanuelIgoldi/Spring-Boot-Profile)
- [Spring Doc](https://github.com/pabloEmanuelIgoldi/Spring-Boot-Swagger)
- [Validate](https://github.com/pabloEmanuelIgoldi/Spring-Boot-Validate)
- [Inter-Service Communication](https://github.com/pabloEmanuelIgoldi/Spring-Boot-Inter-Service-Communication)
##  **Proyectos Spring Data**
- [Spring Data + Redis(Cache)](https://github.com/pabloEmanuelIgoldi/Spring-Data-Redis)





