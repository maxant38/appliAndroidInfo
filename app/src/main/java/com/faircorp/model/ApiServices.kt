package com.faircorp.model

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

// Je regroupe ici l'ensemble des Call pour les différentes API : mon api DAO pour les rooms/fenetres et l'api pour obtenir la meteo
class ApiServices {
    val windowsApiService : WindowApiService by lazy {
        Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                //.baseUrl("https://dev-mind.fr/training/android/")
                .baseUrl("https://app-d45f58a2-9018-4709-947d-995f929abb3f.cleverapps.io:443/api/")
                .build()
                .create(WindowApiService::class.java)
    }


    val roomsApiService: RoomApiService by lazy {
        Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl("https://app-d45f58a2-9018-4709-947d-995f929abb3f.cleverapps.io:443/api/")
                .build()
                .create(RoomApiService::class.java)
    }

    val meteoApiService: MeteoApiService by lazy{
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build()
            .create(MeteoApiService::class.java)
    }



}