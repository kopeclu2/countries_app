package com.develop.countries.rest

import com.develop.countries.exception.CountryNotFoundException
import com.develop.countries.exception.NoRouteFoundException
import jakarta.validation.ConstraintViolationException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.net.URI
import java.time.LocalDateTime

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(ex: ConstraintViolationException): ProblemDetail {
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Input validation failed")
        problemDetail.title = "Validation Error"
        problemDetail.setProperty("timestamp", LocalDateTime.now())

        val violations = ex.constraintViolations.map { violation ->
            mapOf(
                    "field" to violation.propertyPath.toString(),
                    "message" to violation.message
            )
        }

        problemDetail.setProperty("violations", violations)

        return problemDetail
    }

    @ExceptionHandler(NoRouteFoundException::class)
    fun handleNoRouteFound(ex: NoRouteFoundException): ProblemDetail {
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "No route found.")
        problemDetail.title = ex.message
        problemDetail.setProperty("timestamp", LocalDateTime.now())
        return problemDetail
    }

    @ExceptionHandler(CountryNotFoundException::class)
    fun handleNoRouteFound(ex: CountryNotFoundException): ProblemDetail {
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Not valid country cca3 code.")
        problemDetail.title = ex.message
        problemDetail.setProperty("timestamp", LocalDateTime.now())
        return problemDetail
    }
}