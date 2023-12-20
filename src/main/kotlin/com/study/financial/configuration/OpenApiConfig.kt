package com.study.financial.configuration

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfiguration {

    @Value("\${server.url}")
    lateinit var serverUrl: String

    @Bean
    fun customOpenAPI(): OpenAPI {
        val securitySchemeName = "Auth JWT"
        val server = Server().url(serverUrl)
        return OpenAPI()
            .addSecurityItem(SecurityRequirement().addList(securitySchemeName))
            .addServersItem(server)
            .components(
                Components()
                    .addSecuritySchemes(
                        securitySchemeName,
                        SecurityScheme()
                            .name(securitySchemeName)
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT"),
                    ),
            )
            .info(Info().title("Study project for financial").version("v1.0.0"))
    }
}
