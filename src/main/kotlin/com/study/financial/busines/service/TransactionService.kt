package com.study.financial.busines.service

import com.study.financial.busines.ICrudService
import com.study.financial.jpa.entity.CategoryEntity
import com.study.financial.jpa.entity.TransactionEntity
import com.study.financial.jpa.entity.WalletEntity
import com.study.financial.jpa.repository.CategoryJpaRepository
import com.study.financial.jpa.repository.JpaRepositoryWithUserId
import com.study.financial.jpa.repository.TransactionJpaRepository
import com.study.financial.jpa.repository.UserJpaRepository
import com.study.financial.jpa.repository.WalletJpaRepository
import com.study.financial.rest.model.CreateTransaction
import com.study.financial.rest.model.TransactionFilters
import jakarta.persistence.EntityManager
import jakarta.persistence.EntityNotFoundException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TransactionService(
    override val repository: TransactionJpaRepository,
    override val userRepository: UserJpaRepository,
    private val walletJpaRepository: WalletJpaRepository,
    private val categoryJpaRepository: CategoryJpaRepository,
    private val entityManager: EntityManager
) : ICrudService<TransactionEntity, JpaRepositoryWithUserId<TransactionEntity>, CreateTransaction, TransactionFilters> {
    override fun CreateTransaction.toEntity() = TransactionEntity(
        wallet = getWalletById(UUID.fromString(walletId!!)),
        amount = amount!!.toBigDecimal(),
        type = type!!,
        category = getCategoryById(UUID.fromString(categoryId!!)),
        dateTime = dateTime?.let { DateTimeFormatter.ISO_DATE_TIME.parse(it, LocalDateTime::from) }
            ?: LocalDateTime.now()
    )

    @Transactional
    override fun findAll(userId: UUID, pageable: Pageable, filter: TransactionFilters?): Page<TransactionEntity> {
        var sqlFilter = ""
        val params = mutableMapOf<String, Any?>()

        if (!filter?.walletIds.isNullOrEmpty()) {
            sqlFilter += " AND t.wallet.id IN (:walletIds)"
            params["walletIds"] = filter?.walletIds
        }

        if (filter?.categoryIds.isNullOrEmpty()) {
            sqlFilter += " AND t.category.id IN (:categoryIds)"
            params["categoryIds"] = filter?.categoryIds
        }

        if (filter?.to != null) {
            sqlFilter += " AND t.dateTime <= :to"
            params["to"] = DateTimeFormatter.ISO_DATE_TIME.parse(filter.to, LocalDateTime::from)
        }

        if (filter?.from != null) {
            sqlFilter += " AND t.dateTime >= :from"
            params["from"] = DateTimeFormatter.ISO_DATE_TIME.parse(filter.from, LocalDateTime::from)
        }

        val query = entityManager.createQuery(
            """
                SELECT t FROM TransactionEntity t
                WHERE t.wallet.user.id = :userId
                $sqlFilter
            """.trimIndent(),
            TransactionEntity::class.java
        ).apply { params.forEach { (k, v) -> setParameter(k, v) } }
            .setParameter("userId", userId)


        return PageImpl(query.resultList, pageable, query.resultList.size.toLong())


    }

//    override fun save(userId: UUID, model: CreateTransaction): TransactionEntity {
//        val entity = model.toEntity()
//
//        val wallet = entity.wallet
//
//        if (model.type == TransactionEntity.Type.EXPENSE) {
//            wallet.balance -= model.amount!!.toBigDecimal()
//        }
//
//        if (model.type == TransactionEntity.Type.INCOME) {
//            wallet.balance += model.amount!!.toBigDecimal()
//        }
//
//        entity.wallet = wallet
//
//        return repository.save(entity)
//    }

    private fun getWalletById(id: UUID): WalletEntity {
        return walletJpaRepository.getByIdAndUserId(id, currentUserId)
            ?: throw EntityNotFoundException("Wallet with id $id not found")
    }

    private fun getCategoryById(id: UUID): CategoryEntity {
        return categoryJpaRepository.getByIdAndUserId(id, currentUserId)
            ?: throw EntityNotFoundException("Category with id $id not found")
    }

    override val notFoundException: RuntimeException
        get() = EntityNotFoundException("Transaction not found")
}
