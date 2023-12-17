package com.study.financial.busines.service

import com.study.financial.busines.ICrudService
import com.study.financial.jpa.entity.WalletEntity
import com.study.financial.jpa.repository.JpaRepositoryWithUserId
import com.study.financial.jpa.repository.UserJpaRepository
import com.study.financial.jpa.repository.WalletJpaRepository
import com.study.financial.rest.model.CreateWallet
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class WalletService(
    override val repository: WalletJpaRepository,
    override val userRepository: UserJpaRepository,
) : ICrudService<WalletEntity, JpaRepositoryWithUserId<WalletEntity>, CreateWallet> {
    override fun CreateWallet.toEntity() = WalletEntity(
        name = name!!,
        type = type!!,
        balance = balance.toBigDecimal(),
    ).apply {
        user = currentUser
    }

    override val notFoundException: RuntimeException
        get() = EntityNotFoundException("Wallet not found")
}
