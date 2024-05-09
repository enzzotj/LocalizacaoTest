package com.example.androidbusca_v1

data class PetShops(
    var id:Int? = null,
    var nome:String? = null,
    var cnpj:String? = null,
    var cep:String? = null,
    var logradouro:String? = null,
    var complemento:String? = null,
    var bairro:String? = null,
    var localidade:String? = null,
    var uf:String? = null,
    var lng:Double? = null,
    var lat:Double? = null,
    var distance:String? = null
) {

}
