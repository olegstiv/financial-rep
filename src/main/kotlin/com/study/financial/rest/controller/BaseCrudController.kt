package com.study.financial.rest.controller

import com.study.financial.busines.ICrudService
import com.study.financial.busines.service.UserService
import com.study.financial.util.SecurityUtil
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
abstract class BaseCrudController<E : Any, R : JpaRepository<E, UUID>, M : Any, CM> {
    @Autowired
    lateinit var service: ICrudService<E, R, CM>

    @Autowired
    lateinit var userService: UserService

    protected abstract fun E.toModel(): M
    @GetMapping(params = ["page", "size"])
    @Operation(summary = "Получить все элементы")
    fun findAll(
        @PathVariable
        @Schema(
            description = "Номер страницы",
            example = "1",
            defaultValue = "0",
            required = false,
            format = "int",
        )
        page: Int = 0,

        @PathVariable
        @Schema(
            description = "Количество элементов на странице",
            example = "20",
            defaultValue = "20",
            required = false,
            format = "int",
        )
        size: Int = 20,
    ): List<M> {
        val pageable = Pageable.ofSize(size).withPage(page)
        return service.findAll(currentUserId, pageable)
            .map { it.toModel() }
            .content
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить элемент по идентификатору")
    fun findById(
        @PathVariable
        @Schema(
            description = "Идентификатор",
            example = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
            required = true,
            format = "uuid",
        )
        id: String,
    ): M {
        return service.findById(currentUserId, UUID.fromString(id)).toModel()
    }

    @PostMapping
    @Operation(summary = "Создать элемент")
    fun create(model: CM): M {
        return service.save(currentUserId, model).toModel()
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить элемент")
    fun update(
        @PathVariable
        @Schema(
            description = "Идентификатор",
            example = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
            required = true,
            format = "uuid",
        )
        id: String,
        @RequestBody model: CM,
    ): M {
        return service.update(currentUserId, UUID.fromString(id), model).toModel()
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить элемент")
    fun delete(
        @PathVariable
        @Schema(
            description = "Идентификатор",
            example = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
            required = true,
            format = "uuid",
        )
        id: String,
    ) {
        service.delete(currentUserId, UUID.fromString(id))
    }

    private val currentUserId: UUID
        get() = SecurityUtil.currentUserId
}
