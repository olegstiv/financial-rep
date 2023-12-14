package com.study.financial.rest.model

import com.study.financial.jpa.entity.TransactionEntity
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime
import java.util.UUID

data class CreateTransaction(
    @field:Schema(
        description = "Уникальный идентификатор кошелька",
        example = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        format = "uuid",
        required = true,
    )
    @field:NotNull(message = "Кошелек не должен быть null")
    @field:NotBlank(message = "Кошелек не должен быть пустым")
    val walletId: String? = null,

    @field:Schema(description = "Сумма транзакции", required = true, example = "1000")
    val amount: Double? = null,

    @field:Schema(description = "Тип транзакции", required = true, example = "INCOME")
    @field:NotNull(message = "Тип транзакции не должен быть null")
    val type: TransactionEntity.Type? = null,

    @field:Schema(
        description = "Уникальный идентификатор категории",
        example = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        required = true,
        format = "uuid",
    )
    val categoryId: String? = null,

    @field:Schema(description = "Дата и время транзакции", required = true, example = "2021-07-01T00:00:00", format = "date-time")
    val dateTime: String? = null,

)

data class Transaction(
    @field:Schema(
        description = "Уникальный идентификатор транзакции",
        example = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        required = true,
        format = "uuid",
    )
    val id: UUID,

    @field:Schema(
        description = "Уникальный идентификатор кошелька",
        example = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        format = "uuid",
        required = true,
    )
    val walletId: Wallet,

    @field:Schema(description = "Сумма транзакции", required = true, example = "1000")
    val amount: Double,

    @field:Schema(description = "Тип транзакции", required = true, example = "INCOME")
    val type: TransactionEntity.Type,

    @field:Schema(description = "Категория", required = true, example = "Запрлата")
    val category: Category,

    @field:Schema(description = "Дата и время транзакции", required = true, example = "2021-07-01T00:00:00")
    val dateTime: LocalDateTime,

)
