# demo-apache-camel-servlet

Este es un proyecto de ejemplo que utiliza **Apache Camel** con **Spring Boot 3.4.3** y el componente **Servlet** para exponer un servicio REST simple.

## 📌 Descripción

La aplicación proporciona un endpoint REST `/api/hello` que devuelve un mensaje en formato JSON.

## ⚙️ Requisitos previos

- **Java 17** o superior
- **Maven 3.8+**

## 🚀 Ejecución del Proyecto

Puedes ejecutar la aplicación directamente desde tu IDE (IntelliJ, Eclipse, VS Code) o con el siguiente comando en la terminal:

```sh
mvn spring-boot:run
```

## 📡 Endpoints disponibles

| Método | Endpoint     | Descripción                        |
|--------|------------|--------------------------------|
| GET    | `/api/hello` | Retorna un mensaje de saludo en JSON |

## 📄 Ejemplo de respuesta

```json
{
  "mensaje": "¡Hola Mundo con Spring Boot 3.4.3 y Apache Camel usando Servlet!"
}
```

## 📌 Notas

- No se ha implementado autenticación ni documentación con Swagger.
- Se utiliza el componente **Servlet** en lugar de otros como Jetty o Undertow.
- Se puede modificar la configuración en `CamelRoute.java`.

---

¡Listo para probar 🚀! Si tienes dudas o quieres mejorar el proyecto, puedes modificar las rutas en `CamelRoute.java`.