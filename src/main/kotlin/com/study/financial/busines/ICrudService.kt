package com.study.financial.busines

import com.study.financial.jpa.entity.UserEntity
import com.study.financial.jpa.repository.JpaRepositoryWithUserId
import com.study.financial.jpa.repository.UserJpaRepository
import com.study.financial.util.SecurityUtil
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.lang.RuntimeException
import java.util.UUID

interface ICrudService<T : Any, R : JpaRepositoryWithUserId<T>, CM> {

    val repository: R

    val userRepository: UserJpaRepository

    val notFoundException: RuntimeException

    fun CM.toEntity(): T
    fun findAll(userId: UUID, pageable: Pageable): Page<T> {
        return repository.findAllByUserId(userId, pageable)
    }

    fun findById(userId: UUID, id: UUID): T {
        return repository.getByIdAndUserId(id, userId) ?: throw notFoundException
    }

    fun save(userId: UUID, model: CM): T {
        return repository.save(model.toEntity())
    }

    fun update(userId: UUID, id: UUID, model: CM): T {
        return repository.save(model.toEntity())
    }

    fun delete(userId: UUID, id: UUID) {
        repository.deleteByIdAndUserId(id, userId)
    }

    val currentUser: UserEntity
        get() = userRepository.getReferenceById(SecurityUtil.currentUserId)

    val currentUserId: UUID
        get() = SecurityUtil.currentUserId
}
