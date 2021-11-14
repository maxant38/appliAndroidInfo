package com.faircorp.model


//Dto pour les windows

enum class Status { OPEN, CLOSED}

data class WindowDto(val id: Long, val name: String, val windowStatus: Status, val roomName: String, val roomId: Long, val roomCurrentTemperature: Double, val roomTargetTemperature: Double)
