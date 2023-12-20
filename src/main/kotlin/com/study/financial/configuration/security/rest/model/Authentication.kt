package com.study.financial.configuration.security.rest.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class AuthenticationRequest(
    @field:Schema(description = "Имя пользователя", required = true, example = "Иван")
    @field:NotBlank
    val username: String?,

    @field:NotBlank
    @field:Schema(description = "Пароль пользователя", required = true, example = "123456")
    val password: String?,
)

data class AuthenticationResponse(
    val token: String,
)

data class RegisterRequest(
    @field:Pattern(
        regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+\$",
        message = "Email должен быть в формате",
    )
    @field:NotBlank
    @field:Schema(description = "Email пользователя", required = true, example = "ok@ok.ru")
    val email: String?,

    @field:Schema(description = "Имя пользователя", required = true, example = "Иван")
    @field:NotBlank
    val username: String?,

    @field:Schema(description = "Пароль пользователя", required = true, example = "123456")
    @field:NotBlank
    @Size(min = 6, max = 255, message = "Пароль должен быть от 6 до 255 символов")
    val password: String?,
)
