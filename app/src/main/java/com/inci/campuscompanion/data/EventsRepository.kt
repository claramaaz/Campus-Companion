package com.inci.campuscompanion.data

import com.inci.campuscompanion.model.CampusEvent
import com.inci.campuscompanion.network.CampusApi

class EventsRepository(private val api: CampusApi) {
    suspend fun getEvents(): List<CampusEvent> =
        api.getEvents().map { dto ->
            CampusEvent(
                id = dto.id,
                title = dto.name,
                timeLabel = dto.startingDate.replace("T", " ").take(16),
                locationLabel = dto.address ?: "TBA"
            )
        }
}