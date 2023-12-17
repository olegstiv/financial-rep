package com.study.financial.jpa.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.NoRepositoryBean
import java.util.UUID

@NoRepositoryBean
interface JpaRepositoryWithUserId<T : Any> : JpaRepository<T, UUID> {
    @Query("select c from #{#entityName} c where c.id = :id and c.user.id = :userId")
    fun getByIdAndUserId(id: UUID, userId: UUID): T?

    @Query("select c from #{#entityName} c where c.user.id = :userId")
    fun findAllByUserId(userId: UUID, pageable: Pageable): Page<T>

    @Modifying
    @Query("delete from #{#entityName} c where c.id = :id and c.user.id = :userId")
    fun deleteByIdAndUserId(id: UUID, userId: UUID)

    @Query("select count(c) > 0 from #{#entityName} c where c.id = :id and c.user.id = :userId")
    fun checkExistsByIdAndUserId(id: UUID, userId: UUID): Boolean
}
