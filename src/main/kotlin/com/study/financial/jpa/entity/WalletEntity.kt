package com.study.financial.jpa.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.UUID

@Entity
@Table(name = "wallets")
class WalletEntity(

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    val id: UUID = UUID.randomUUID(),

    @Column(name = "name", nullable = false)
    var name: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    var type: Type = Type.CASH,

    @Column(name = "balance", nullable = false)
    var balance: BigDecimal = BigDecimal.ZERO,

) {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    lateinit var user: UserEntity

    fun addBalance(amount: BigDecimal) {
        balance += amount
    }

    fun subtractBalance(amount: BigDecimal) {
        balance -= amount
    }

    fun updateBalance(amount: BigDecimal) {
        balance = amount
    }

    enum class Type {
        CASH,
        CARD,
    }
}
