package com.example.androidbusca_v1

data class DirectionsResponse(
    val routes: List<Route>? = null
)

data class Route(
    val legs: List<Leg>? = null
)

data class Leg(
    val distance: Distance? = null
)

data class Distance(
    val text: String? = null,
    val value: Int
)
