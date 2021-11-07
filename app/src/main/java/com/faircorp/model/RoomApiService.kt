package com.faircorp.model


import retrofit2.Call
import retrofit2.http.*

//Room API service

interface RoomApiService {
    @GET("rooms")
    fun findAll(): Call<List<RoomDto>>

    @GET("rooms/{id}")
    fun findById(@Path("id") id: Long): Call<RoomDto>

    @PUT("rooms/{id}")
    fun updateRoom(@Path("id") id: Long, @Body room: RoomDto) : Call<RoomDto>

    @POST
    fun createRoom(@Body room: RoomDto) : Call<RoomDto>
}