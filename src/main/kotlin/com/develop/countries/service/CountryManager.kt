package com.develop.countries.service

import com.develop.countries.exception.CountryNotFoundException
import com.develop.countries.model.PathResponse
import org.springframework.stereotype.Service

@Service
class CountryManager(
        private val countryPathFinder: CountryPathFinder
) {

    /**
     * Finds the shortest path between two countries.
     * @param origin The origin country's code.
     * @param destination The destination country's code.
     * @return A list of country codes representing the path.
     * @throws CountryNotFoundException If no path is found or if either country does not exist.
     */
    @Throws(CountryNotFoundException::class)
    fun findPath(origin: String, destination: String): PathResponse {
        val path = countryPathFinder.findPath(origin, destination)
        return PathResponse(path)
    }
}