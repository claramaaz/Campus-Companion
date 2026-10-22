package com.inci.campuscompanion.network

import kotlinx.serialization.Serializable

@Serializable
data class EventDto(
    val id: String,
    val name: String,
    val description: String? = null,
    val address: String? = null,
    val startingDate: String
)