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
    val localizacao = MutableLiveData(Location())
    val listPetShop = MutableLiveData(SnapshotStateList<PetShops>())
    val key = "";

    init {
        searchLocalization()
    }

    fun searchLocalization() {
        CoroutineScope(Dispatchers.IO).launch{
            try {
                val response = geolocation.postGeolocate(key)
                if (response.isSuccessful) {
                    localizacao.postValue(response.body()?.location)
                } else {
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
                val response = petShops.getPetShop();
                if (response.isSuccessful) {
                    listPetShop.value!!.clear()
                    val petShopList: List<PetShops>? = response.body()
                    for (petShop in petShopList.orEmpty()) {
                        val location: Location? = petShop.cep?.let { buscaPorCep(it) }
                        location?.let {
                            petShop.lat = it.lat
                            petShop.lng = it.lng

                            val locUser = localizacao.value
                            val origin : String = locUser?.lat.toString() + "," + locUser?.lng.toString()
                            val destination : String = petShop.lat.toString() + "," + petShop.lng.toString()
                            petShop.distance = distancia(origin, destination)
                        }
                    }
                    val sortedList = petShopList.orEmpty().sortedBy { parseDistance(it.distance) ?: Double.MAX_VALUE }
                    listPetShop.value!!.addAll(sortedList)

                } else {
                    erroApi.postValue(response.errorBody()!!.string())
                }
            }catch (e: Exception) {
                Log.e("api", "Deu ruim no get! ${e.message}")
                erroApi.postValue(e.message)
            }
        }
    }

    fun parseDistance(distanceStr: String?): Double? {
        return distanceStr?.let {
            val regex = """([0-9.]+)\s+km""".toRegex()
            val matchResult = regex.find(it)
            matchResult?.groupValues?.get(1)?.toDoubleOrNull()
        }
    }


    suspend fun distancia(origin: String, destination: String): String {
        try {
            val response = maps.getDirections(key, origin, destination)
            if (response.isSuccessful) {
                return response.body()?.routes?.getOrNull(0)?.legs?.getOrNull(0)?.distance?.text ?: ""

            } else {
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