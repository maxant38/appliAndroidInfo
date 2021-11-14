package com.faircorp.model


import retrofit2.Call
import retrofit2.http.*

//Interface pour faire les différentes appels (GET,PUT,POST) interagir avec l'api que j'ai crée et ainsi obtenir la liste des rooms par exemple

interface RoomApiService {
    @GET("rooms")
    fun findAll(): Call<List<RoomDto>> //j'obtiens la liste de l'ensemble des rooms

    @GET("rooms/{id}")
    fun findById(@Path("id") id: Long): Call<RoomDto> //j'obtiens les informations sur une room en particulier

    @PUT("rooms/{id}")
    fun updateRoom(@Path("id") id: Long, @Body room: RoomDto) : Call<RoomDto> //je modifie une room en particulier

    @POST
    fun createRoom(@Body room: RoomDto) : Call<RoomDto> //je crée une nouvelle room
}