package com.faircorp.model

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiServices {
    val windowsApiService : WindowApiService by lazy {
        Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                //.baseUrl("https://dev-mind.fr/training/android/")
                .baseUrl("http://joe.alhasrouni.cleverapps.io/api/")
                .build()
                .create(WindowApiService::class.java)
    }



}