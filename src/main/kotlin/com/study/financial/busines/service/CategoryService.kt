package com.study.financial.busines.service

import com.study.financial.busines.ICrudService
import com.study.financial.jpa.entity.CategoryEntity
import com.study.financial.jpa.repository.CategoryJpaRepository
import com.study.financial.jpa.repository.JpaRepositoryWithUserId
import com.study.financial.jpa.repository.UserJpaRepository
import com.study.financial.rest.model.CreateCategory
import com.study.financial.rest.model.Filters
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class CategoryService(
    override val repository: CategoryJpaRepository,
    override val userRepository: UserJpaRepository,
) : ICrudService<CategoryEntity, JpaRepositoryWithUserId<CategoryEntity>, CreateCategory, Filters> {

    override val notFoundException: RuntimeException
        get() = EntityNotFoundException("Category not found")

    override fun CreateCategory.toEntity(): CategoryEntity {
        return CategoryEntity(
            name = name!!,
            description = description,
            icon = icon,
        ).apply {
            user = currentUser
        }
    }
}
