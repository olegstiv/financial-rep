package com.study.financial.rest.controller

import com.study.financial.jpa.entity.TransactionEntity
import com.study.financial.jpa.repository.JpaRepositoryWithUserId
import com.study.financial.rest.model.Category
import com.study.financial.rest.model.CreateTransaction
import com.study.financial.rest.model.Transaction
import com.study.financial.rest.model.TransactionFilters
import com.study.financial.rest.model.Wallet
import com.study.financial.util.SecurityUtil.currentUserId
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import java.time.LocalDateTime
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/transactions/")
@Tag(name = "Transaction", description = "API для работы с транзакциями")
class TransactionController() :
    BaseCrudController<TransactionEntity, JpaRepositoryWithUserId<TransactionEntity>, Transaction, CreateTransaction, TransactionFilters>() {
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


    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        summary = "Получить все элементы",
        security = [SecurityRequirement(name = "Auth JWT")],
    )
    override fun findAll(
        @ParameterObject pageable: Pageable,
        @ParameterObject filter: TransactionFilters?,
    ): List<Transaction> {
        return service.findAll(currentUserId, pageable, filter)
            .map { it.toModel() }
            .content
    }

    //    Download csv findAll
    @GetMapping("download/csv/", produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        summary = "Получить все элементы",
        security = [SecurityRequirement(name = "Auth JWT")],
    )
    fun findAllCsv(
        @ParameterObject pageable: Pageable,
        @ParameterObject filter: TransactionFilters?,
    ): ResponseEntity<ByteArray> {
        val content = service.findAll(currentUserId, pageable, filter)
            .map { it.toModel() }
            .content

        val header = "wallet,amount,type,category,dateTime"
        val csv =
            content.joinToString("\n") { "${it.wallet.name},${it.amount},${it.type.name},${it.category.name},${it.dateTime}" }

        val headers = HttpHeaders().apply {
            add(HttpHeaders.CONTENT_TYPE, "text/csv; charset=utf-8")
            add(
                "Content-Disposition", "attachment; filename=\"export_transaction_${
                    LocalDateTime.now().format(
                        java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss")
                    )
                }.csv\""
            )
        }

        return ResponseEntity.ok().headers(headers).body(csv.toByteArray())
    }
}
