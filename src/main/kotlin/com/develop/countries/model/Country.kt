package com.develop.countries.model

/**
 * Represents a country with a name and a list of bordering countries.
 */
data class Country(
    val name: String,
    val borders: List<String>
)