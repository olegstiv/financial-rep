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
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class TransactionService(
    override val repository: TransactionJpaRepository,
    override val userRepository: UserJpaRepository,
    private val walletJpaRepository: WalletJpaRepository,
    private val categoryJpaRepository: CategoryJpaRepository,
) : ICrudService<TransactionEntity, JpaRepositoryWithUserId<TransactionEntity>, CreateTransaction> {
    override fun CreateTransaction.toEntity() = TransactionEntity(
        wallet = getWalletById(UUID.fromString(walletId!!)),
        amount = amount!!.toBigDecimal(),
        type = type!!,
        category = getCategoryById(UUID.fromString(categoryId!!)),
        dateTime = LocalDateTime.parse(dateTime) ?: LocalDateTime.now(),
    )

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
