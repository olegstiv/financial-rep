package com.study.financial.configuration.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.study.financial.rest.ErrorDetails
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler
import org.springframework.stereotype.Component
import java.io.IOException
import java.io.OutputStream
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val authenticationProvider: AuthenticationProvider,
) {

    @Autowired
    @Qualifier("customAuthenticationEntryPoint")
    private lateinit var authEntryPoint: AuthenticationEntryPoint

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain? = http
        .csrf { it.disable() }
        .authorizeHttpRequests {
            it
                .requestMatchers("/auth/**", "/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                .requestMatchers("/public/**").permitAll()
                .anyRequest().authenticated()
        }
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        .logout {
            it
                .logoutUrl("/logout")
                .logoutSuccessHandler(HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
                .invalidateHttpSession(true)
        }
        .exceptionHandling { it.authenticationEntryPoint(authEntryPoint) }
//        .cors {  it.configurationSource(corsConfigurationSource()) }
        .build()


//    @Bean
//    fun corsConfigurationSource(): CorsConfigurationSource {
//        val configuration = CorsConfiguration().apply {
//            allowedOriginPatterns = listOf("*")
//            allowedOrigins = listOf("*")
//            allowedMethods = listOf("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
//            allowedHeaders = listOf("*")
//            allowCredentials = true
//        }
//
//        return UrlBasedCorsConfigurationSource().also {
//            it.registerCorsConfiguration("/**", configuration)
//        }
//    }

}

@Configuration
@EnableWebMvc
class CorsConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**").allowedOrigins("*")
    }
}

@Component("customAuthenticationEntryPoint")
class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {
    @Throws(IOException::class, ServletException::class)
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException,
    ) {
        val re = ErrorDetails(HttpStatus.UNAUTHORIZED.toString(), "Authentication failed")
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        val responseStream: OutputStream = response.outputStream
        val mapper = ObjectMapper()
        mapper.writeValue(responseStream, re)
        responseStream.flush()
    }
}
