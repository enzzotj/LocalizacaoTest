package com.example.androidbusca_v1

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiMaps {

    @GET("geocode/json")
    suspend fun getGeocode(@Query("key") key:String, @Query("address") address:String): Response<LocationReponse>

    @GET("directions/json")
    suspend fun getDirections(@Query("key") key:String, @Query("origin") origin:String, @Query("destination") destination:String): Response<DirectionsResponse>

}