package com.example.androidbusca_v1

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    val BASE_URL_MAPS = "https://maps.googleapis.com/maps/api/"

    val BASE_URL_PETS = "http://:8080/api/"

    val BASE_URL_GEOLOCATION = "https://www.googleapis.com/geolocation/v1/"

    fun getApiMaps(): ApiMaps {
        val cliente =
            Retrofit.Builder()
                .baseUrl(BASE_URL_MAPS)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiMaps::class.java)

        return cliente
    }

    fun getPetShop(): ApiPetShop {
        val cliente =
            Retrofit.Builder()
                .baseUrl(BASE_URL_PETS)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiPetShop::class.java)

        return cliente
    }

    fun getGeolocation(): ApiGeolocation {
        val cliente =
            Retrofit.Builder()
                .baseUrl(BASE_URL_GEOLOCATION)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiGeolocation::class.java)

        return cliente
    }
}