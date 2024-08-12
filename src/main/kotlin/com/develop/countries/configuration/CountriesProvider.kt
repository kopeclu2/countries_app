package com.develop.countries.configuration

import com.develop.countries.model.CountryJson
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import java.util.function.Supplier

@Component
class CountriesProvider(
        @Value("\${app.countries.file}")
        private val resource: Resource,
        private val objectMapper: ObjectMapper
) : Supplier<Map<String, CountryJson>> {

    lateinit var countries: Map<String, CountryJson>

    override fun get(): Map<String, CountryJson> = if (::countries.isInitialized) countries else emptyMap()

    @PostConstruct
    fun load() {
        countries = objectMapper.readValue(resource.file, object : TypeReference<List<CountryJson>>() {})
                .associateBy { country -> country.cca3 }
    }
}