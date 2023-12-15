package com.study.financial.configuration.security.rest.controller

import com.study.financial.busines.service.UserService
import com.study.financial.configuration.security.rest.model.AuthenticationRequest
import com.study.financial.configuration.security.rest.model.RegisterRequest
import io.swagger.v3.oas.annotations.security.SecurityRequirements
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.security.auth.callback.ConfirmationCallback.OK

@RestController
@RequestMapping("/auth")
@SecurityRequirements()
class AuthenticationController(
    private val userService: UserService,
) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    fun register(@RequestBody registerRequest: RegisterRequest) = userService.register(registerRequest)

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    fun authenticate(@RequestBody authenticationRequest: AuthenticationRequest) =
        userService.authenticate(authenticationRequest)
}
