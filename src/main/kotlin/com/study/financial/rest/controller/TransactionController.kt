package com.study.financial.rest.controller

import com.study.financial.jpa.entity.TransactionEntity
import com.study.financial.jpa.repository.JpaRepositoryWithUserId
import com.study.financial.rest.model.Category
import com.study.financial.rest.model.CreateTransaction
import com.study.financial.rest.model.Transaction
import com.study.financial.rest.model.Wallet
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/transactions/")
@Tag(name = "Transaction", description = "API для работы с транзакциями")
class TransactionController() :
    BaseCrudController<TransactionEntity, JpaRepositoryWithUserId<TransactionEntity>, Transaction, CreateTransaction>() {
    override fun TransactionEntity.toModel(): Transaction {
        return Transaction(
            id = id,
            wallet = Wallet(
                id = wallet.id,
                name = wallet.name,
                type = wallet.type,
                balance = wallet.actualBalance
                    .let { if (type == TransactionEntity.Type.EXPENSE) it - amount else it + amount }.toDouble(),
            ),
            amount = amount.toDouble(),
            type = type,
            category = Category(
                id = category.id,
                name = category.name,
                description = category.description,
                icon = category.icon,
            ),
            dateTime = dateTime,

        )
    }
}
