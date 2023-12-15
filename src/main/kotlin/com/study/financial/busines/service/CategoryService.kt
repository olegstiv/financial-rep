package com.study.financial.busines.service

import com.study.financial.busines.ICrudService
import com.study.financial.jpa.entity.CategoryEntity
import com.study.financial.jpa.repository.CategoryJpaRepository
import com.study.financial.jpa.repository.UserJpaRepository
import com.study.financial.rest.model.CreateCategory
import org.springframework.stereotype.Service

@Service
class CategoryService(
    override val repository: CategoryJpaRepository,
    override val userRepository: UserJpaRepository,
) : ICrudService<CategoryEntity, CategoryJpaRepository, CreateCategory> {
    override fun CreateCategory.toEntity(): CategoryEntity {
        return CategoryEntity(
            name = name!!,
            description = description,
            icon = icon,
        ).apply {
            user = getCurrentUser()
        }
    }
}
