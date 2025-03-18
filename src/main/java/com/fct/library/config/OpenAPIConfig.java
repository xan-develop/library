package com.fct.library.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Libreria API",
                description = "API para la gestión de libros y prestamos",
                version = "1.0.0",
                termsOfService = "https://alexander.com/terms",
                contact = @Contact(
                        name = "Alexander",
                        email = "alexander.garcia.external@eviden.com",
                        url = "https://alex.com"
                ),
                license = @License(
                        name = "Licencia MIT",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Entorno de desarrollo local"),
                @Server(url = "https://api.tu-sitio.com", description = "Entorno de producción")
        }
)
public class OpenAPIConfig {
}
