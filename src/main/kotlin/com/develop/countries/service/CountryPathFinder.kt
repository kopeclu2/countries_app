package com.develop.countries.service

import com.develop.countries.configuration.CountriesProvider
import com.develop.countries.exception.CountryNotFoundException
import com.develop.countries.exception.NoRouteFoundException
import com.develop.countries.model.Country
import org.springframework.stereotype.Component
import java.util.Queue
import java.util.LinkedList

/**
 * Class to manage the countries and find paths between them.
 */
@Component
class CountryPathFinder(
        private val countriesProvider: CountriesProvider
) {

    /**
     * Uses BFS to find the shortest path between two countries.
     * @param origin The starting country's code.
     * @param destination The destination country's code.
     * @return A list of country codes representing the path.
     * @throws CountryNotFoundException If no path is found or if either country does not exist.
     */
    @Throws(CountryNotFoundException::class, NoRouteFoundException::class)
    fun findPath(origin: String, destination: String): List<String> {
        val start = origin.uppercase()
        val end = destination.uppercase()

        if (start == end) {
            return listOf(start)
        }

        val countries = countriesProvider.get()

        // Check if both start and end countries exist in the map
        if (!countries.containsKey(start)) {
            throw CountryNotFoundException("Country with code $start not found.")
        }

        if (!countries.containsKey(end)) {
            throw CountryNotFoundException("Country with code $end not found.")
        }

        // Initialize the BFS queue with the start country
        val queue: Queue<List<String>> = LinkedList()
        queue.add(listOf(start))

        // Set to keep track of visited countries to avoid cycles
        val visited = mutableSetOf<String>()

        // Perform BFS
        while (queue.isNotEmpty()) {
            // Dequeue the next path
            val path = queue.remove()
            // Get the last country in the current path
            val country = path.last()

            // If the last country is the destination, return the path
            if (country == end) {
                return path
            }

            // If the country has not been visited, explore its neighbors
            if (!visited.contains(country)) {
                visited.add(country)
                // Iterate through each neighboring country
                for (neighbor in countries[country]?.borders ?: emptyList()) {
                    // Create a new path by adding the neighbor to the current path
                    val newPath = path.toMutableList()
                    newPath.add(neighbor)
                    // Enqueue the new path for further exploration
                    queue.add(newPath)
                }
            }
        }

        // If no path is found, throw an exception
        throw NoRouteFoundException(start, end)
    }
}