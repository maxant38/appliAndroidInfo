package com.faircorp.model

import retrofit2.Call
import retrofit2.http.*

// Interface pour faire l'appel GET à l'api méteo
interface MeteoApiService {

    @GET("weather?q=Saint-Etienne,fr&APPID=d6e2c97f41145b13916ae1b9f06164c8") // ici j'ai decidé d'avoir les information sur la ville de Saint Etienne et j'ai mis ma cle api
    fun getMeteo(): Call<MeteoDto>



}