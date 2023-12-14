package com.study.financial.busines.service

import com.study.financial.jpa.repository.UserJpaRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    val userJpaRepository: UserJpaRepository,
)
