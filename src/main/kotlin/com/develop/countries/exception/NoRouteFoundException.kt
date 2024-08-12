package com.develop.countries.exception

class NoRouteFoundException(val origin: String, val end: String): Exception("Route between $origin and $end, has not been found.")