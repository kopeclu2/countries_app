package com.develop.countries.validator

import com.develop.countries.annotation.ValidCountryCode
import com.develop.countries.configuration.CountriesProvider
import com.develop.countries.service.CountryManager
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext


class CountryCodeValidator(
    private val countriesProvider: CountriesProvider
) : ConstraintValidator<ValidCountryCode, String> {

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        // Ensure the value is not null and is a valid country code
        return value != null && isValidCountryCode(value)
    }

    private fun isValidCountryCode(code: String): Boolean {
        return countriesProvider.countries.containsKey(code.uppercase())
    }
}