package com.study.financial.jpa.repository

import com.study.financial.jpa.entity.TransactionEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TransactionJpaRepository : JpaRepositoryWithUserId<TransactionEntity> {

    @Query("select count(c) > 0 from TransactionEntity c where c.id = :id and c.wallet.user.id = :userId")
    override fun checkExistsByIdAndUserId(id: UUID, userId: UUID): Boolean

    @Modifying
    @Query("delete from TransactionEntity c where c.id = :id and c.wallet.user.id = :userId")
    override fun deleteByIdAndUserId(id: UUID, userId: UUID)

    @Query("select c from TransactionEntity c where c.id = :id and c.wallet.user.id = :userId")
    override fun getByIdAndUserId(id: UUID, userId: UUID): TransactionEntity?

    @Query("select c from TransactionEntity c where c.wallet.user.id = :userId")
    override fun findAllByUserId(userId: UUID, pageable: Pageable): Page<TransactionEntity>
}
