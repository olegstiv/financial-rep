package com.study.financial.jpa.repository

import com.study.financial.jpa.entity.WalletEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface WalletJpaRepository : JpaRepository<WalletEntity, UUID>
