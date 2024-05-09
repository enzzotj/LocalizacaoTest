package com.example.androidbusca_v1

data class LocationResponse(
    val results: List<Results>? = null
)

data class Results(
    val geometry: Geometry? = null
)

data class Geometry(
    val location: Location? = null
)

