package com.study.financial.rest.model

import com.study.financial.jpa.entity.TransactionEntity
import com.study.financial.validation.ValidUUID
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import java.time.LocalDateTime
import java.util.UUID

data class CreateTransaction(
    @field:Schema(
        description = "Уникальный идентификатор кошелька",
        example = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        format = "uuid",
        required = true,
    )
    @field:NotBlank(message = "Кошелек не должен быть пустым")
    @field:ValidUUID
    @field:Size(min = 36, max = 36, message = "Кошелек должен быть 36 символов")
    val walletId: String? = null,

    @field:Schema(description = "Сумма транзакции", required = true, example = "1000")
    @field:NotNull(message = "Сумма транзакции не должна быть null")
    @field:Positive(message = "Сумма транзакции должна быть больше 0")
    @field:Max(value = 1_000_000_000, message = "Сумма транзакции должна быть меньше 1_000_000_000")
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
    @field:ValidUUID
    @field:NotBlank(message = "Категория не должна быть пустой")
    @Size(min = 36, max = 36, message = "Категория должна быть 36 символов")
    val categoryId: String? = null,

    @field:Schema(
        description = "Дата и время транзакции",
        required = false,
        format = "date-time",
    )
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
        description = "Кошелек",
        required = true,
    )
    val wallet: Wallet,

    @field:Schema(description = "Сумма транзакции", required = true, example = "1000")
    val amount: Double,

    @field:Schema(description = "Тип транзакции", required = true, example = "INCOME")
    val type: TransactionEntity.Type,

    @field:Schema(description = "Категория", required = true)
    val category: Category,

    @field:Schema(description = "Дата и время транзакции", required = true, example = "2021-07-01T00:00:00")
    val dateTime: LocalDateTime,

)
