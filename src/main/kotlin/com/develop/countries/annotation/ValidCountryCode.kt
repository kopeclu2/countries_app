package com.develop.countries.annotation

import com.develop.countries.validator.CountryCodeValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [CountryCodeValidator::class])
annotation class ValidCountryCode(
        val message: String = "Invalid cca3 country code",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = []
)