package com.study.financial.jpa.repository

import com.study.financial.jpa.entity.CategoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CategoryJpaRepository : JpaRepository<CategoryEntity, UUID>
