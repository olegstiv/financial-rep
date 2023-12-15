package com.study.financial.jpa.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCrypt
import java.util.UUID

@Entity
@Table(name = "users")
class UserEntity(
    @Column(name = "username", unique = true, nullable = false)
    var login: String,

    password: String,

    @Column(name = "email", unique = true, nullable = false)
    var email: String,
) : UserDetails {

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

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf()
    }

    override fun getPassword(): String {
        return passwordHash
    }

    override fun getUsername(): String {
        return login
    }

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}
