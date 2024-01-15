package com.study.financial.rest

import com.study.financial.exception.RegisterException
import jakarta.persistence.EntityNotFoundException
import mu.KLogging
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException::class)
    fun entityNotFound(exception: EntityNotFoundException): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(
            exception.message ?: "Entity not found",
            "Entity not found",
        )

        return ResponseEntity(errorDetails, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(RegisterException::class)
    fun registerException(exception: RegisterException): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(
            exception.message,
            "Register exception",
        )
        return ResponseEntity(errorDetails, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidException(exception: MethodArgumentNotValidException): ResponseEntity<ErrorDetails> {
        val message = exception.bindingResult.fieldErrors.joinToString(", ") {
            "${it.field} ${it.defaultMessage}"
        }

        val errorDetails = ErrorDetails(
            message,
            "Method argument not valid",
        )

        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    @ResponseBody
    fun handleException(ex: Exception, request: WebRequest?): ResponseEntity<*> {
        val errorDetails = ErrorDetails(
            ex.message ?: "Unknown error",
            request?.getDescription(false) ?: "Unknown request",
            ex.stackTraceToString(),
        )

        return ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR)
            .also {
                log.error(ex.message, ex)
            }
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException::class)
    @ResponseBody
    fun handleException(ex: HttpMediaTypeNotSupportedException, request: WebRequest?): ResponseEntity<*> {
        val errorDetails = ErrorDetails(
            ex.message ?: "Unknown error",
            request?.getDescription(false) ?: "Unknown request",
            ex.stackTraceToString(),
        )

        return ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.UNSUPPORTED_MEDIA_TYPE)
            .also {
                log.error(ex.message, ex)
            }
    }


    companion object: KLogging()
}

data class ErrorDetails(
    val message: String,
    val details: String,
    val trace: String? = null,
)
