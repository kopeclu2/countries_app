package com.develop.countries.rest

import com.develop.countries.annotation.ValidCountryCode
import com.develop.countries.model.PathResponse
import com.develop.countries.service.CountryManager
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/routing")
@Validated
class CountriesRestController(
        private val countryManager: CountryManager
) {

    /**
     * Handles GET requests to find the route between two countries.
     * @param origin The origin country's code.
     * @param destination The destination country's code.
     * @return A ResponseEntity containing the path or an error message.
     */
    @GetMapping("/{origin}/{destination}")
    fun getPath(
            @Valid @PathVariable @ValidCountryCode origin: String,
            @Valid @PathVariable @ValidCountryCode destination: String
    ): ResponseEntity<PathResponse> = countryManager.findPath(origin, destination).let { route ->
        ResponseEntity.ok(route)
    }
}