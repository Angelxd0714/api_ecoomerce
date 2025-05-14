# api_ecommerce

Este proyecto es una API RESTful desarrollada con Spring Boot para un e-commerce. Proporciona funcionalidades esenciales para la gestión de productos, categorías, usuarios y pedidos.

## Características principales

*   **Gestión de productos:** Permite crear, listar, modificar y eliminar productos, incluyendo su nombre, descripción, precio, stock e imagen.
*   **Gestión de categorías:** Permite crear, listar, modificar y eliminar categorías de productos.
*   **Gestión de usuarios:** Permite crear, listar, modificar y eliminar usuarios, incluyendo su información personal y credenciales de acceso.
*   **Gestión de pedidos:** Permite crear, listar, modificar y eliminar pedidos, incluyendo la información del usuario, los productos incluidos, la fecha y el estado.
*   **Autenticación y autorización:** Implementa un sistema de autenticación y autorización para proteger las rutas de la API y garantizar que solo los usuarios autorizados puedan acceder a ellas.
*   **Base de datos:** Utiliza una base de datos relacional (especificar cuál se usa en el proyecto, ej. PostgreSQL, MySQL) para el almacenamiento de datos.
*   **Documentación:** (Si existe, especificar) Se provee documentación de la API utilizando (ej. Swagger, OpenAPI).

## Tecnologías utilizadas



- **Spring Boot:** Es un framework de Java que simplifica el desarrollo de aplicaciones web y microservicios. Proporciona una configuración predeterminada y convenciones para agilizar el desarrollo y la implementación de aplicaciones.

- **Spring Data JPA:** Es una capa de abstracción que simplifica el acceso a la base de datos utilizando el patrón Repository. Proporciona métodos predefinidos para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sin necesidad de escribir consultas SQL manualmente.

- **Spring Security:** Es un módulo de Spring que proporciona seguridad y autenticación a la API. Permite proteger las rutas de la API y controlar el acceso de los usuarios mediante autenticación y autorización.

- **Lombok:** Es una biblioteca de Java que ayuda a reducir la cantidad de código repetitivo y boilerplate. Proporciona anotaciones para generar automáticamente getters, setters, constructores, métodos equals y hashCode, entre otros.

- **Spring Data:** Se refiere a los módulos de Spring que facilitan la integración con diferentes bases de datos. En este caso, se mencionan MySql y Redis, que son bases de datos relacionales y de almacenamiento en caché, respectivamente.

- **Spring Cloud Stream:** Es un proyecto de Spring que simplifica la integración de aplicaciones basadas en microservicios mediante el uso de sistemas de mensajería. En este caso, se menciona RabbitMQ, que es un sistema de mensajería basado en el protocolo AMQP (Advanced Message Queuing Protocol).



## 🧱 Arquitectura y Diseño

### Arquitectura RESTful

La API implementa el estilo de arquitectura **REST** (Representational State Transfer), lo que significa que:

- Los **recursos** (como productos, categorías o pedidos) están representados por **URLs** claras y predecibles (por ejemplo, `/products`, `/orders`).
- Se utilizan los métodos **HTTP** estándar:
  - `GET` para obtener recursos,
  - `POST` para crear nuevos recursos,
  - `PUT` o `PATCH` para actualizarlos,
  - `DELETE` para eliminarlos.
- El formato de intercambio de datos es **JSON**, lo que facilita la comunicación entre clientes web, móviles o terceros sistemas.

Esta arquitectura permite que la API sea **escalable**, **mantenible** y fácilmente **integrable** con otros servicios o frontends.

---

### Arquitectura por Capas

La aplicación está organizada en varias capas que separan responsabilidades claramente:

1. **Capa de presentación (Controller):**  
   Expone los endpoints de la API, recibe las solicitudes HTTP y delega la lógica de negocio a la capa de servicio.

2. **Capa de servicio (Service):**  
   Contiene la lógica de negocio. Se encarga de procesar los datos, aplicar reglas y coordinar las operaciones entre repositorios y controladores.

3. **Capa de acceso a datos (Repository):**  
   Interactúa directamente con la base de datos utilizando **Spring Data JPA**. Permite realizar operaciones CRUD sin necesidad de escribir consultas SQL manuales.

4. **Capa de modelo (Model o Entity):**  
   Define las entidades del dominio, representando tablas en la base de datos.

Esta separación favorece el principio de **responsabilidad única (SRP)** y facilita las pruebas unitarias y la mantenibilidad.

---

### Patrones de diseño utilizados

La implementación también incluye algunos **patrones de diseño** comunes que mejoran la calidad del código:

- **Builder Pattern:**  
  Utilizado para construir objetos complejos (como entidades o DTOs) de forma controlada, especialmente cuando hay múltiples atributos opcionales. Es frecuente junto con **Lombok** usando `@Builder`.

- **Factory Method Pattern:**  
  Utilizado para encapsular la lógica de creación de objetos, permitiendo decidir en tiempo de ejecución qué instancia devolver. Este patrón puede verse en servicios o helpers que crean pedidos, usuarios o respuestas dependiendo del contexto.

- **DTO (Data Transfer Object):**  
  Aunque no es un patrón de diseño formal, se usan DTOs para transferir datos entre capas de manera eficiente y controlada, evitando exponer directamente las entidades del modelo.

## 📐 Diagramas de Arquitectura

### 1. Arquitectura por capas en Spring Boot
![Arquitectura por capas en Spring Boot](https://imgs.search.brave.com/hMAg-x1fvnF3JQ6kV0lJby6leGhyeHjS33eYXTR7G3Q/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9yZWFj/dGl2ZXByb2dyYW1t/aW5nLmlvL19uZXh0/L2ltYWdlP3VybD0v/ZmlndXJlcy9jYXBh/cy5wbmcmdz0xOTIw/JnE9NzU)
> Fuente: https://reactiveprogramming.io/blog/es/estilos-arquitectonicos/capasGeeks

---

### 2. Flujo de autenticación con Spring Security
![Flujo de autenticación con Spring Security](https://imgs.search.brave.com/TVCFaEbA-6-2LLaay8t0PbdGeixggCdzHFM7mRZRPSY/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9pMi53/cC5jb20vdGVjaG5p/Y2Fsc2FuZC5jb20v/d3AtY29udGVudC91/cGxvYWRzLzIwMjAv/MDkvU3ByaW5nLXNl/Y3VyaXR5LnBuZz9m/aXQ9MTM2Niw3Njgm/c3NsPTE)
> Fuente: Spring.io

---

### 3. Arquitectura de microservicios para e-commerce
![Microservicios para e-commerce](https://www.researchgate.net/publication/354007516/figure/fig1/AS:1059920668882944@1628754604523/E-commerce-microservices-architecture.ppm)
> Fuente: ResearchGate

---

### 4. Diagrama entidad-relación (ERD) para e-commerce
![ERD e-commerce](https://vertabelo.com/blog/img/2020/04/ER-Diagram-Online-Shop.png)
> Fuente: Vertabelo

---

### 5. Comunicación con RabbitMQ
![RabbitMQ](https://www.rabbitmq.com/img/tutorials/amqp-concepts.png)
> Fuente: RabbitMQ.com

## Cómo ejecutar el proyecto

1.  Clona el repositorio: `git clone https://github.com/Angelxd0714/api_ecoomerce.git`
2.  Abre el proyecto en tu IDE preferido (ej. IntelliJ IDEA, Eclipse).
3.  Configura la conexión a la base de datos en el archivo `application.properties` o `application.yml`.
4.  Ejecuta la aplicación Spring Boot.

## Endpoints de la API

(Si existe documentación, enlazarla aquí)

Se provee una lista de los endpoints principales de la API:

*   `/Products`: Gestión de productos.
*   `/Categories`: Gestión de categorías.
*   `/Users`: Gestión de usuarios.
*   `/Orders`: Gestión de pedidos.
*   `/Car`: Carrito de productos.
*   `/Marker`: Etiqueta de productos.
*   `/Payment`: pagos.
*   `/Permission`:Lista Permisos.
*   `/Rols`:Roles de usuario.
*   `/HistorySold`: Historial de ventas.
*   `/HistoryBought`: historial de compras.  



## Contribución

Las contribuciones son bienvenidas. Si deseas colaborar con el proyecto, por favor, abre un issue o envía un pull request.

## Licencia

Version 1.0

## Contacto

*   Angel Santibañez.
*   https://www.linkedin.com/in/angel-ti
*   +57 3242167883