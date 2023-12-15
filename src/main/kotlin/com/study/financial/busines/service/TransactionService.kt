package com.study.financial.busines.service

import com.study.financial.busines.ICrudService
import com.study.financial.jpa.entity.TransactionEntity
import com.study.financial.jpa.repository.TransactionJpaRepository
import com.study.financial.jpa.repository.UserJpaRepository
import com.study.financial.rest.model.CreateTransaction
import org.springframework.stereotype.Service

@Service
class TransactionService(
    override val repository: TransactionJpaRepository,
    override val userRepository: UserJpaRepository,
) : ICrudService<TransactionEntity, TransactionJpaRepository, CreateTransaction> {
    override fun CreateTransaction.toEntity(): TransactionEntity {
        TODO("Not yet implemented")
    }
}
