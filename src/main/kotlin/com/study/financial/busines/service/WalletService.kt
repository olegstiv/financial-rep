package com.study.financial.busines.service

import com.study.financial.busines.ICrudService
import com.study.financial.jpa.entity.WalletEntity
import com.study.financial.jpa.repository.WalletJpaRepository
import com.study.financial.rest.model.CreateWallet
import org.springframework.stereotype.Service

@Service
class WalletService(
    override val repository: WalletJpaRepository,
) : ICrudService<WalletEntity, WalletJpaRepository, CreateWallet> {
    override fun CreateWallet.toEntity(): WalletEntity {
        TODO("Not yet implemented")
    }
}
