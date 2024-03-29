package com.study.financial.rest.controller

import com.study.financial.jpa.entity.WalletEntity
import com.study.financial.jpa.repository.JpaRepositoryWithUserId
import com.study.financial.rest.model.CreateWallet
import com.study.financial.rest.model.Filters
import com.study.financial.rest.model.Wallet
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/wallets/")
@Tag(name = "Wallet", description = "API для работы с кошельком")
class WalletController() :
    BaseCrudController<WalletEntity, JpaRepositoryWithUserId<WalletEntity>, Wallet, CreateWallet, Filters>() {
    override fun WalletEntity.toModel(): Wallet {
        return Wallet(
            id = id,
            name = name,
            type = type,
            balance = actualBalance.toDouble(),
        )
    }
}
