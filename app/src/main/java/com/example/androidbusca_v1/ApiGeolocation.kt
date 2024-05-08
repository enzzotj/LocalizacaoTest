package com.example.androidbusca_v1

import GeolocationResponse
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiGeolocation {

    @POST("geolocate")
    suspend fun postGeolocate(@Query("key") key:String): Response<GeolocationResponse>

}