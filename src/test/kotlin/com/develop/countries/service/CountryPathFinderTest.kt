package com.develop.countries.service

import com.develop.countries.configuration.CountriesProvider
import com.develop.countries.exception.CountryNotFoundException
import com.develop.countries.exception.NoRouteFoundException
import com.develop.countries.model.CountryJson
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class CountryPathFinderSpec : StringSpec({
    "should find a direct path between two countries" {
        val countriesProvider = mockk<CountriesProvider>()
        val countryPathFinder = CountryPathFinder(countriesProvider)

        val countryA = mockk<CountryJson>()
        val countryB = mockk<CountryJson>()
        val countryC = mockk<CountryJson>()

        every { countryA.borders } returns listOf("B")
        every { countryB.borders } returns listOf("C")
        every { countryC.borders } returns emptyList()

        every { countriesProvider.get() } returns mapOf(
                "A" to countryA,
                "B" to countryB
        )

        val result = countryPathFinder.findPath("A", "B")
        result shouldBe listOf("A", "B")
    }

    "should find a path through intermediate countries" {
        val countriesProvider = mockk<CountriesProvider>()
        val countryPathFinder = CountryPathFinder(countriesProvider)

        val countryA = mockk<CountryJson>()
        val countryB = mockk<CountryJson>()
        val countryC = mockk<CountryJson>()

        every { countryA.borders } returns listOf("B")
        every { countryB.borders } returns listOf("C")

        every { countriesProvider.get() } returns mapOf(
                "A" to countryA,
                "B" to countryB,
                "C" to countryC
        )

        val result = countryPathFinder.findPath("A", "C")
        result shouldBe listOf("A", "B", "C")
    }

    "should throw CountryNotFoundException if origin country is not found" {
        val countriesProvider = mockk<CountriesProvider>()
        val countryPathFinder = CountryPathFinder(countriesProvider)

        val countryB = mockk<CountryJson>()
        val countryC = mockk<CountryJson>()

        every { countriesProvider.get() } returns mapOf(
                "B" to countryB,
                "C" to countryC
        )

        val exception = shouldThrow<CountryNotFoundException> {
            countryPathFinder.findPath("X", "B")
        }
        exception.message shouldBe "Country with code X not found."
    }

    "should throw CountryNotFoundException if destination country is not found" {
        val countriesProvider = mockk<CountriesProvider>()
        val countryPathFinder = CountryPathFinder(countriesProvider)

        val countryA = mockk<CountryJson>()
        val countryC = mockk<CountryJson>()

        every { countriesProvider.get() } returns mapOf(
                "A" to countryA,
                "C" to countryC
        )

        val exception = shouldThrow<CountryNotFoundException> {
            countryPathFinder.findPath("A", "X")
        }
        exception.message shouldBe "Country with code X not found."
    }

    "should throw NoRouteFoundException if no route is found between countries" {
        val countriesProvider = mockk<CountriesProvider>()
        val countryPathFinder = CountryPathFinder(countriesProvider)

        val countryA = mockk<CountryJson>()
        val countryB = mockk<CountryJson>()

        every { countryA.borders } returns listOf()

        every { countriesProvider.get() } returns mapOf(
                "A" to countryA,
                "B" to countryB
        )

        val exception = shouldThrow<NoRouteFoundException> {
            countryPathFinder.findPath("A", "B")
        }
        exception.message shouldBe "Route between A and B, has not been found."
    }
})