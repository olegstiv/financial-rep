package com.study.financial.rest.controller

import com.study.financial.jpa.entity.CategoryEntity
import com.study.financial.jpa.repository.JpaRepositoryWithUserId
import com.study.financial.rest.model.Category
import com.study.financial.rest.model.CreateCategory
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/categories/")
@Tag(name = "Category", description = "API для работы с категорями")
class CategoryController() :
    BaseCrudController<CategoryEntity, JpaRepositoryWithUserId<CategoryEntity>, Category, CreateCategory>() {
    override fun CategoryEntity.toModel(): Category {
        return Category(
            id = id,
            name = name,
            description = description,
            icon = icon,
        )
    }
}
