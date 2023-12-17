package com.study.financial.jpa.repository

import com.study.financial.jpa.entity.CategoryEntity
import org.springframework.stereotype.Repository

@Repository
interface CategoryJpaRepository : JpaRepositoryWithUserId<CategoryEntity>
