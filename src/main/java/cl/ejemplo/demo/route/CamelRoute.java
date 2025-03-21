package cl.ejemplo.demo.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * Clase CamelRoute - Configura y maneja un servicio REST con Apache Camel con component servlet.
 * Esta clase define una ruta Camel que responde a solicitudes GET en el endpoint "/api/hello".
 *
 * Autor: @dapavez
 * Fecha de Creación: Marzo 2025
 * Versión: 1.0
 * Descripción:
 *  Esta clase utiliza Apache Camel para crear una API REST básica que responde con un mensaje estático en formato texto plano.
 *
 * Historial de Modificaciones:
 *  - [Fecha] - Descripción de cambios o mejoras realizadas
 *  
 */
@Component// Anotación que marca esta clase como un componente de Spring, habilitando la gestión de dependencias por parte del contenedor de Spring.
public class CamelRoute extends RouteBuilder {

    @Override
    public void configure() {
        
        // Configuración del componente REST con Servlet y OpenAPI
        restConfiguration()
            .component("servlet") // Define que el componente REST utilizará el servlet embebido
            .contextPath("/api") // Define el contexto base de los endpoints REST
            .apiContextPath("/api-docs") // Ruta donde se expondrá la documentación OpenAPI
            .apiProperty("api.title", "API de Ejemplo con Camel - servlet") // Título de la API
            .apiProperty("api.version", "1.0.0") // Versión de la API
            .apiProperty("api.description", "API de Ejemplo con Camel") // Descripción de la API
            .apiProperty("cors", "true") // Habilita CORS para los endpoints
            .dataFormatProperty("prettyPrint", "true") // Formatea la salida JSON de manera legible
            .bindingMode(RestBindingMode.json) // Define el modo de enlace JSON para los endpoints
            .host("localhost") // Especifica el host donde se ejecuta el servicio
            .port(8080); // Define el puerto donde se expone la API

        // Definición del endpoint REST
        rest("/hello")
            .id("hello-route") // Identificador único de la ruta
            .description("Endpoint de ejemplo con Apache Camel") // Descripción del endpoint en OpenAPI
            .produces("application/json") // Define el tipo de respuesta que produce el endpoint
            .bindingMode(RestBindingMode.auto) // Modo de enlace automático de datos
            .get()
                .description("Devuelve un saludo en JSON") // Descripción específica del método GET
                .outType(Map.class) // Define el tipo de salida como un mapa JSON
                .responseMessage()
                    .code(200).message("OK - Respuesta exitosa") // Define la respuesta HTTP esperada
                .endResponseMessage()
            .to("direct:miRuta"); // Redirige la solicitud a la ruta interna "direct:miRuta"

        // Definición de la ruta de procesamiento
        from("direct:miRuta")
	        .doTry()
	            .process(exchange -> {
	                // Configura la respuesta con tipo de contenido JSON
	                exchange.getIn().setHeader("Content-Type", "application/json");
	                // Establece el cuerpo de la respuesta con un mensaje de saludo
	                exchange.getIn().setBody(Map.of("mensaje", "¡Hola Mundo con Spring Boot 3.4.3 y Apache Camel usando Servlet!"));
	            })
	        .doCatch(Exception.class) // Manejo de excepciones
	            .process(exchange -> {
	                // Configura la respuesta en caso de error con tipo de contenido de texto plano
	                exchange.getIn().setHeader("Content-Type", "text/plain");
	                // Mensaje de error genérico
	                exchange.getIn().setBody("Ocurrió un error inesperado.");
	                // Código de respuesta HTTP 500 (Error interno del servidor)
	                exchange.getIn().setHeader("CamelHttpResponseCode", 500);
	            })
	        .end(); // Fin del bloque try-catch
    }
}