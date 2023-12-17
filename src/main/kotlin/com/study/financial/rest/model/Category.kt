package com.study.financial.rest.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.util.UUID

data class CreateCategory(
    @field:NotBlank(message = "Название категории не должно быть пустым")
    @field:NotNull(message = "Название категории не должно быть null")
    @field:Schema(description = "Название категории", required = true, example = "Зарплата")
    val name: String? = null,

    @field:Schema(description = "Описание категории", required = false, example = "Зарплата")
    val description: String? = null,

    @field:Schema(description = "Иконка категории", required = false, example = "icon")
    val icon: String? = null,

)

data class Category(
    @field:Schema(
        description = "Уникальный идентификатор категории",
        example = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        required = true,
    )
    val id: UUID,
    @field:Schema(description = "Название категории", required = true, example = "Зарплата")
    val name: String,
    @field:Schema(description = "Описание категории", required = false, example = "Зарплата каждый месяц от работодателя")
    val description: String? = null,
    @field:Schema(description = "Иконка категории", required = false, example = "icon")
    val icon: String? = null,

)
