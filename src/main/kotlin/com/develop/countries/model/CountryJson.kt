package com.develop.countries.model

data class CountryJson(
        val name: Name,
        val tld: List<String>,
        val cca2: String,
        val ccn3: String,
        val cca3: String,
        val cioc: String,
        val independent: Boolean,
        val status: String,
        val unMember: Boolean,
        val currencies: Map<String, Currency>,
        val idd: Idd,
        val capital: List<String>,
        val altSpellings: List<String>,
        val region: String,
        val subregion: String,
        val languages: Map<String, String>,
        val translations: Map<String, Translation>,
        val latlng: List<Double>,
        val landlocked: Boolean,
        val borders: List<String>,
        val area: Double,
        val flag: String,
        val demonyms: Map<String, Demonym>
)

data class Name(
        val common: String,
        val official: String,
        val native: Map<String, NativeName>
)

data class NativeName(
        val official: String,
        val common: String
)

data class Currency(
        val name: String,
        val symbol: String
)

data class Idd(
        val root: String,
        val suffixes: List<String>
)

data class Translation(
        val official: String,
        val common: String
)

data class Demonym(
        val f: String,
        val m: String
)