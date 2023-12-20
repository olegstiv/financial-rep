package com.study.financial.rest.controller

import com.study.financial.busines.ICrudService
import com.study.financial.busines.service.UserService
import com.study.financial.jpa.repository.JpaRepositoryWithUserId
import com.study.financial.rest.ErrorDetails
import com.study.financial.util.SecurityUtil
import com.study.financial.validation.ValidUUID
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import java.util.UUID
import org.springdoc.core.annotations.ParameterObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@ApiResponses(
    ApiResponse(
        responseCode = "401",
        description = "Не авторизован",
        content = arrayOf(Content(schema = Schema(implementation = ErrorDetails::class))),
    ),
    ApiResponse(
        responseCode = "404",
        description = "Элемент не найден",
        content = arrayOf(Content(schema = Schema(implementation = ErrorDetails::class))),
    ),
    ApiResponse(
        responseCode = "500",
        description = "Внутренняя ошибка сервера",
        content = arrayOf(Content(schema = Schema(implementation = ErrorDetails::class))),
    ),
    ApiResponse(
        responseCode = "201",
        description = "Успешное выполнение",
        content = arrayOf(Content(schema = Schema(implementation = Any::class))),
    ),
    ApiResponse(
        responseCode = "204",
        description = "Успешное выполнение",
        content = arrayOf(),
    ),

)
abstract class BaseCrudController<E : Any, R : JpaRepositoryWithUserId<E>, M : Any, CM> {
    @Autowired
    lateinit var service: ICrudService<E, R, CM>

    @Autowired
    lateinit var userService: UserService

    protected abstract fun E.toModel(): M

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        summary = "Получить все элементы",
        security = [SecurityRequirement(name = "Auth JWT")],
    )
    fun findAll(
        @ParameterObject pageable: Pageable,
    ): List<M> {
        return service.findAll(currentUserId, pageable)
            .map { it.toModel() }
            .content
    }

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Получить элемент по идентификатору", security = [SecurityRequirement(name = "Auth JWT")])
    fun findById(
        @ValidUUID
        @PathVariable
        @Schema(
            description = "Идентификатор",
            example = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
            required = true,
            format = "uuid",
        )
        id: String,
    ): ResponseEntity<M> {
        val model = service.findById(currentUserId, UUID.fromString(id)).toModel()
        return ResponseEntity.ok(model)
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Создать элемент", security = [SecurityRequirement(name = "Auth JWT")])
    fun create(
        @Valid @RequestBody
        model: CM,
    ): ResponseEntity<M> {
        val created = service.save(currentUserId, model).toModel()
        return ResponseEntity.status(HttpStatus.CREATED).body(created)
    }

    @PutMapping("/{id}", consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Обновить элемент", security = [SecurityRequirement(name = "Auth JWT")])
    fun update(
        @ValidUUID
        @PathVariable
        @Schema(
            description = "Идентификатор",
            example = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
            required = true,
            format = "uuid",
        )
        id: String,
        @Valid @RequestBody
        model: CM,
    ): ResponseEntity<M> {
        val updated = service.update(currentUserId, UUID.fromString(id), model).toModel()
        return ResponseEntity.ok(updated)
    }

    @DeleteMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Удалить элемент", security = [SecurityRequirement(name = "Auth JWT")])
    fun delete(
        @ValidUUID
        @PathVariable
        @Schema(
            description = "Идентификатор",
            example = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
            required = true,
            format = "uuid",
        )
        id: String,
    ): ResponseEntity<Unit> {
        service.delete(currentUserId, UUID.fromString(id))

        return ResponseEntity.noContent().build()
    }

    private val currentUserId: UUID
        get() = SecurityUtil.currentUserId
}
