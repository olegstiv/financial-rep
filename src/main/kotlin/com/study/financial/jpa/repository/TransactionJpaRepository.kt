package com.study.financial.jpa.repository

import com.study.financial.jpa.entity.TransactionEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TransactionJpaRepository : JpaRepository<TransactionEntity, UUID>
