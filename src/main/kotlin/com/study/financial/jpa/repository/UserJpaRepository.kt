package com.study.financial.jpa.repository

import com.study.financial.jpa.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserJpaRepository : JpaRepository<UserEntity, UUID> {
    fun findByLogin(login: String): UserEntity?

    fun findByLoginOrEmail(login: String, email: String): UserEntity?
}
