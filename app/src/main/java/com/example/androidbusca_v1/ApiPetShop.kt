package com.example.androidbusca_v1

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiPetShop {

    @GET("pet-shop")
    suspend fun getPetShop(): Response<List<PetShops>>

}