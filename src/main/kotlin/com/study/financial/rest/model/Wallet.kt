package com.study.financial.rest.model

import com.study.financial.jpa.entity.WalletEntity
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import java.util.UUID

data class CreateWallet(
    @field:NotBlank(message = "Название кошелька не должно быть пустым")
    @field:NotNull(message = "Название кошелька не должно быть null")
    @field:Schema(description = "Название кошелька", required = true, example = "Карта Тинькоф")
    val name: String? = null,

    @field:NotNull(message = "Тип кошелька не должен быть null")
    @field:Schema(description = "Тип кошелька", required = true, example = "CARD")
    val type: WalletEntity.Type? = null,

    @field:NotNull(message = "Баланс кошелька не должен быть null")
    @field:PositiveOrZero(message = "Баланс кошелька должен быть положительным числом")
    @field:Schema(description = "Баланс кошелька", required = true, example = "0", defaultValue = "0")
    val balance: Double = 0.0,
)

data class Wallet(
    @field:Schema(
        description = "Уникальный идентификатор кошелька",
        example = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        required = true,
    )
    val id: UUID,
    @field:Schema(description = "Название кошелька", required = true, example = "Карта Тинькоф")
    val name: String,
    @field:Schema(description = "Тип кошелька", required = true, example = "CARD")
    val type: WalletEntity.Type,
    @field:Schema(description = "Баланс кошелька", required = true, example = "0", defaultValue = "0")
    val balance: Double,
)
