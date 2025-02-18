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

*   **Spring Boot:** Framework de Java para el desarrollo de aplicaciones web y microservicios.
*   **Spring Data JPA:** Simplifica el acceso a la base de datos utilizando el patrón Repository.
*   **Spring Security:** Proporciona seguridad y autenticación a la API.
*   **(Especificar otras tecnologías si se usan, ej. Lombok, MapStruct, etc.)**
*   **(Especificar la base de datos utilizada, ej. PostgreSQL, MySQL)**

## Arquitectura

La API sigue una arquitectura RESTful, utilizando los métodos HTTP (GET, POST, PUT, DELETE) para realizar operaciones sobre los recursos. Se utiliza el formato JSON para el intercambio de datos entre el cliente y el servidor.

## Cómo ejecutar el proyecto

1.  Clona el repositorio: `git clone https://github.com/Angelxd0714/api_ecoomerce.git`
2.  Abre el proyecto en tu IDE preferido (ej. IntelliJ IDEA, Eclipse).
3.  Configura la conexión a la base de datos en el archivo `application.properties` o `application.yml`.
4.  Ejecuta la aplicación Spring Boot.

## Endpoints de la API

(Si existe documentación, enlazarla aquí)

Se provee una lista de los endpoints principales de la API:

*   `/products`: Gestión de productos.
*   `/categories`: Gestión de categorías.
*   `/users`: Gestión de usuarios.
*   `/orders`: Gestión de pedidos.

(Añadir más endpoints si existen)

## Contribución

Las contribuciones son bienvenidas. Si deseas colaborar con el proyecto, por favor, abre un issue o envía un pull request.

## Licencia

(Especificar la licencia del proyecto)

## Contacto

*   Angel David Tlaseca (enlace a tu perfil de LinkedIn, si lo deseas)