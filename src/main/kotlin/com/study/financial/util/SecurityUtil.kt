package com.study.financial.util

import com.study.financial.jpa.entity.UserEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import java.util.UUID

object SecurityUtil {
    val currentUserId: UUID
        get() =
            when (val authentication = SecurityContextHolder.getContext().authentication) {
                is JwtAuthenticationToken -> UUID.fromString(authentication.token.subject)
                else -> (authentication.principal as UserEntity).id
            }
}
