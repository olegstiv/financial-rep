package com.study.financial.jpa.repository

import com.study.financial.jpa.entity.WalletEntity
import org.springframework.stereotype.Repository

@Repository
interface WalletJpaRepository : JpaRepositoryWithUserId<WalletEntity>
