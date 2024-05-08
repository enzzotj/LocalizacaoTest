package com.example.androidbusca_v1

import GeolocationResponse
import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PetShopViewModel:ViewModel() {

    val petShops = RetrofitService.getPetShop()
    val maps = RetrofitService.getApiMaps()
    val geolocation = RetrofitService.getGeolocation()
    val erroApi = MutableLiveData("")
    val localizacao = MutableLiveData(GeolocationResponse())
    val listPetShop = MutableLiveData(SnapshotStateList<PetShops>())
    val key = "";
    val v = MutableLiveData("");

    init {
        searchLocalization()
    }

    fun searchLocalization() {
        CoroutineScope(Dispatchers.IO).launch{
            try {
                val response = geolocation.postGeolocate(key)

                if (response.isSuccessful) {
                    localizacao.postValue(response.body())
                    val test: String = distancia("-23.4514382,-46.7319839", "-23.4539037,-46.7374229");
                    erroApi.postValue(test)
                    val tt: Location? = buscaPorCep("01414-001");
                    v.postValue(tt?.lat.toString())
                } else {
                    Log.e("api", response.errorBody()!!.string())
                    erroApi.postValue(response.errorBody()!!.string())
                }

            } catch (e: Exception) {
                Log.e("api", "Deu ruim no get! ${e.message}")
                erroApi.postValue(e.message)
            }
        }
    }

    fun getPetShop(){
        CoroutineScope(Dispatchers.IO).launch{
            try {
                Log.e("api", "aaaaaaaaaaaaaaaaaaaaaaaaa")
                val response = petShops.getPetShop();
                if (response.isSuccessful) {
                    //teste.postValue(response.body())
                } else {
                    Log.e("api", "aaaaaaaaaaaaaaaaaaaaaaaaa")
                    erroApi.postValue(response.errorBody()!!.string())
                }
            }catch (e: Exception) {
                Log.e("api", "Deu ruim no get! ${e.message}")
                erroApi.postValue(e.message)
            }
        }
    }


    suspend fun distancia(origin: String, destination: String): String {
        try {
            val response = maps.getDirections(key, origin, destination)
            if (response.isSuccessful) {
                return response.body()?.routes?.getOrNull(0)?.legs?.getOrNull(0)?.distance?.text ?: ""

            } else {
                Log.e("api", response.errorBody()?.string() ?: "Erro desconhecido")
                erroApi.postValue(response.errorBody()?.string() ?: "Erro desconhecido")
                return ""
            }
        } catch (e: Exception) {
            Log.e("api", "Deu ruim no get! ${e.message}")
            erroApi.postValue(e.message ?: "Erro desconhecido")
            return ""
        }
    }

    suspend fun buscaPorCep(cep: String): Location? {
        try {
            val response = maps.getGeocode(key, cep)
            if (response.isSuccessful) {
                    return response.body()?.results?.getOrNull(0)?.geometry?.location
            } else {
                Log.e("api", "Erro na requisição: ${response.errorBody()?.string()}")
                erroApi.postValue(response.errorBody()?.string() ?: "Erro desconhecido")
                return null
            }
        } catch (e: Exception) {
            Log.e("api", "Deu ruim no get! ${e.message}")
            erroApi.postValue(e.message ?: "Erro desconhecido")
            return null
        }
    }


}