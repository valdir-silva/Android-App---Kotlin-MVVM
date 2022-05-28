package com.example.events.data

import com.example.events.data.response.EventResponse
import retrofit2.Call
import retrofit2.http.Field

interface Service {

    fun getEvents(): Call<List<EventResponse>>

    fun checkIn(
        @Field("eventId") eventId: String? = null,
        @Field("name") name: String,
        @Field("email") email: String
    ): Call<Boolean>
}