package com.faircorp.model

import retrofit2.Call
import retrofit2.http.*

//Interface pour faire les différentes appels (GET,PUT,POST) interagir avec l'api que j'ai crée et ainsi obtenir la liste des windows par exemple


interface WindowApiService {
    @GET("windows")
    fun findAll(): Call<List<WindowDto>> //j'obtiens la liste de l'ensemble des windows

    @GET("windows/{id}")
    fun findById(@Path("id") id: Long): Call<WindowDto>  //j'obtiens l'ensemble des informations sur une window en particulier

    @PUT("windows/{id}/switch")
    fun updateWindow(@Path("id") id: Long): Call<WindowDto> //Je modifie le status d'une window en particulier

    @POST("windows")
    fun createWindow(@Body window: WindowDto): Call<WindowDto> // Je crée une nouvelle window
}