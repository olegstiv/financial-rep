package com.study.financial.busines

import java.util.UUID
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ICrudService<T : Any, R : JpaRepository<T, UUID>, CM> {

    val repository: R

    fun CM.toEntity(): T
    fun findAll(userId: UUID, pageable: Pageable): Page<T> {
        return repository.findAll(pageable)
    }

    fun findById(userId: UUID, id: UUID): T {
        return repository.findById(id).orElseThrow()
    }

    fun save(userId: UUID, model: CM): T {
        return repository.save(model.toEntity())
    }

    fun update(userId: UUID, id: UUID, model: CM): T {
        return repository.save(model.toEntity())
    }

    fun delete(userId: UUID, id: UUID) {
        repository.deleteById(id)
    }
}
