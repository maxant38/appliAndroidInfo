package com.faircorp.model

import retrofit2.Call
import retrofit2.http.*

interface WindowApiService {
    @GET("windows")
    fun findAll(): Call<List<WindowDto>>

    @GET("windows/{id}")
    fun findById(@Path("id") id: Long): Call<WindowDto>

    @PUT("windows/{id}")
    fun updateWindow1(@Path("id") id: Long, @Body window: WindowDto) : Call<WindowDto>

    @PUT("windows/{id}/switch")
    fun updateWindow(@Path("id") id: Long): Call<WindowDto>

    @POST("windows")
    fun createWindow(@Body window: WindowDto): Call<WindowDto>
}