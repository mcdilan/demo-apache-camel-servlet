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
@Component
public class CamelRoute extends RouteBuilder {

    @Override
    public void configure() {

        // Configurar el componente REST con Servlet y definir el contexto base
        restConfiguration()
            .component("servlet")
            .contextPath("/api")
            .dataFormatProperty("prettyPrint", "true") 
            .enableCORS(true);

        rest("/hello")
            .id("hello-route")
            .description("Endpoint de ejemplo con Apache Camel")
            .produces("application/json")
            .bindingMode(RestBindingMode.auto)
            .get()
                .description("Devuelve un saludo en JSON")
                .responseMessage()
                    .code(200).message("OK - Respuesta exitosa")
                .endResponseMessage()
            .to("direct:miRuta");

        from("direct:miRuta")
        .log(">>> Procesando solicitud en /api/hello")
        .setHeader("Content-Type", constant("application/json"))
        .setBody(exchange -> Map.of("mensaje", "¡Hola Mundo con Spring Boot 3.4.3 y Apache Camel usando Servlet!"))
        .marshal().json(JsonLibrary.Jackson);  
        
    }
}


