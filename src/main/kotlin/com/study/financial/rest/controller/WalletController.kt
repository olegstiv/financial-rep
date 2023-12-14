package com.study.financial.rest.controller

import com.study.financial.jpa.entity.WalletEntity
import com.study.financial.jpa.repository.WalletJpaRepository
import com.study.financial.rest.model.CreateWallet
import com.study.financial.rest.model.Wallet
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/wallets/", produces = ["application/json"], consumes = ["application/json"])
@Tag(name = "Wallet", description = "API для работы с кошельком")
class WalletController() : BaseCrudController<WalletEntity, WalletJpaRepository, Wallet, CreateWallet>() {
    override fun WalletEntity.toModel(): Wallet {
        return Wallet(
            id = id,
            name = name,
            type = type,
            balance = balance.toDouble(),
        )
    }
}
