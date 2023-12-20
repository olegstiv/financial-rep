package com.study.financial.rest.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.util.UUID

data class CreateCategory(
    @field:NotBlank(message = "Название категории не должно быть пустым")
    @field:Size(min = 3, max = 255, message = "Название категории должно быть от 3 до 255 символов")
    @field:Schema(description = "Название категории", required = true, example = "Зарплата")
    val name: String? = null,

    @field:Schema(description = "Описание категории", required = false, example = "Зарплата")
    @field:Size(min = 3, max = 255, message = "Описание категории должно быть от 3 до 255 символов")
    val description: String? = null,

    @field:Schema(description = "Иконка категории", required = false, example = "icon")
    @field:Size(min = 3, max = 255, message = "Иконка категории должно быть от 3 до 255 символов")
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
    @field:Schema(
        description = "Описание категории",
        required = false,
        example = "Зарплата каждый месяц от работодателя",
    )
    val description: String? = null,
    @field:Schema(description = "Иконка категории", required = false, example = "icon")
    val icon: String? = null,

)
