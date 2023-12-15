package com.study.financial.configuration.security.rest.model

data class AuthenticationRequest(
    val username: String,
    val password: String,
)

data class AuthenticationResponse(
    val token: String,
)

data class RegisterRequest(
    val email: String,
    val username: String,
    val password: String,
)
