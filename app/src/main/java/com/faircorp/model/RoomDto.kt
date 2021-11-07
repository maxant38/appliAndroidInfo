package com.faircorp.model


    data class RoomDto(val id: Long,
                       val name: String,
                       val currentTemperature: Double?,
                       var targetTemperature: Double?)
