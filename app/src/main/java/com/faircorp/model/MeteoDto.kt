package com.faircorp.model

//Dto pour l'api meteo ou j'ai selectionne les quatres informations qui m'interesse dans la réponse de l'api : le temperature, l'icon, une description et l'humidité

data class detailTemperature(val temp: Float, val humidity : Float)

data class detailWeather(val main: String, val icon : String)

data class MeteoDto (val main: detailTemperature, val weather: List<detailWeather>)

