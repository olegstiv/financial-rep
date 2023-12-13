package com.study.financial.jpa.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID
import org.springframework.security.crypto.bcrypt.BCrypt

@Entity
@Table(name = "users")
class UserEntity(
    @Column(name = "username", unique = true, nullable = false)
    var username: String,

    password: String,

    @Column(name = "email", unique = true, nullable = false)
    var email: String,
) {

    init {
        setPassword(password)
    }

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    val id: UUID = UUID.randomUUID()

    @Column(name = "password_hash", nullable = false)
    private lateinit var passwordHash: String

    fun checkPassword(password: String): Boolean {
        return BCrypt.checkpw(password, passwordHash)
    }

    fun setPassword(password: String) {
        passwordHash = BCrypt.hashpw(password, BCrypt.gensalt())
    }
}
