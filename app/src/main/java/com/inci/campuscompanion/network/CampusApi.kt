package com.inci.campuscompanion.network

import retrofit2.http.GET

interface CampusApi {
    @GET("events/event")
    suspend fun getEvents(): List<EventDto>
}