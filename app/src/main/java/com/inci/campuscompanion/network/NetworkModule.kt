package com.inci.campuscompanion.network

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object NetworkModule {
    private val json = Json { ignoreUnknownKeys = true }

    val api: CampusApi = Retrofit.Builder()
        .baseUrl("https://6ad70838f71f154ac4879fac.mockapi.io/api/v1/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(CampusApi::class.java)
}