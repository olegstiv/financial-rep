package com.study.financial.busines.service

import com.study.financial.configuration.security.JwtService
import com.study.financial.configuration.security.rest.model.AuthenticationRequest
import com.study.financial.configuration.security.rest.model.AuthenticationResponse
import com.study.financial.configuration.security.rest.model.RegisterRequest
import com.study.financial.exception.RegisterException
import com.study.financial.jpa.entity.UserEntity
import com.study.financial.jpa.repository.UserJpaRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
class UserService(
    private val userJpaRepository: UserJpaRepository,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager,
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return userJpaRepository.findByLogin(username)
            ?: throw UsernameNotFoundException("User not found")
    }

    fun register(registerRequest: RegisterRequest): AuthenticationResponse {
        if (userJpaRepository.findByLoginOrEmail(registerRequest.username!!, registerRequest.email!!) != null) {
            throw RegisterException()
        }

        val user = UserEntity(
            email = registerRequest.email,
            login = registerRequest.username,
            password = registerRequest.password!!,
        )

        userJpaRepository.save(user)

        val token = jwtService.generateToken(user)

        return AuthenticationResponse(token)
    }

    fun authenticate(@Validated authenticationRequest: AuthenticationRequest): AuthenticationResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authenticationRequest.username,
                authenticationRequest.password,
            ),
        )

        val user = userJpaRepository.findByLogin(authenticationRequest.username!!)
            ?: throw EntityNotFoundException("User not found")

        return AuthenticationResponse(jwtService.generateToken(user))
    }
}
