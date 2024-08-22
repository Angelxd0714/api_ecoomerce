package com.ecommerce.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(title = "Ecommerce API", version = "1.0", description = "Ecommerce API Documentation", contact = @io.swagger.v3.oas.annotations.info.Contact(name = "Angel Santibañez", email = "angelxd0714@gmail.com")), servers = {
        @io.swagger.v3.oas.annotations.servers.Server(url = "http://localhost:8080", description = "Local Server")
}

)

@SecurityScheme(

        type = SecuritySchemeType.HTTP, name = "bearerAuth", scheme = "bearer", bearerFormat = "JWT", description = "Enter JWT token")
public class SwaggerConf {

}
