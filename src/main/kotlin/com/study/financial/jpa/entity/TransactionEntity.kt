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
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "transactions")
class TransactionEntity(

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    val id: UUID = UUID.randomUUID(),

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    var wallet: WalletEntity,

    @Column(name = "amount", nullable = false)
    var amount: BigDecimal,

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    var type: Type,

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    var category: CategoryEntity,

    @Column(name = "date_time", nullable = false)
    var dateTime: LocalDateTime = LocalDateTime.now(),
) {

    enum class Type {
        INCOME,
        EXPENSE,
    }
}
