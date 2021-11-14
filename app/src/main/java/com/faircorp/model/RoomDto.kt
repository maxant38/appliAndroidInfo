package com.faircorp.model

//Dto pour les roooms

    data class RoomDto(val name: String,
                       val id: Long,
                       val currentTemperature: Double?,
                       var targetTemperature: Double?,
                       val floor: Long?,
                       val buildingId: Long)
