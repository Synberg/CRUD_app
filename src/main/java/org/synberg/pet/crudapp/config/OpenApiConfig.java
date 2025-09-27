package org.synberg.pet.crudapp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Library CRUD API",
                version = "v1",
                description = "REST API для пользователей, книг и выдач",
                contact = @Contact(name = "Synberg")
        ),
        servers = {
                @Server(url = "/", description = "Local API base")
        }
)
public class OpenApiConfig { }

