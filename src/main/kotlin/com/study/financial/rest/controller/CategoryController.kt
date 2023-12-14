package com.study.financial.rest.controller

import com.study.financial.jpa.entity.CategoryEntity
import com.study.financial.jpa.repository.CategoryJpaRepository
import com.study.financial.rest.model.Category
import com.study.financial.rest.model.CreateCategory
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/categories/", produces = ["application/json"], consumes = ["application/json"])
@Tag(name = "Category", description = "API для работы с категорями")
class CategoryController() :
    BaseCrudController<CategoryEntity, CategoryJpaRepository, Category, CreateCategory>() {
    override fun CategoryEntity.toModel(): Category {
        return Category(
            id = id,
            name = name,
            description = description,
            icon = icon,

        )
    }
}
