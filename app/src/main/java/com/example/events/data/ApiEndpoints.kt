package com.example.events.data

import com.example.events.data.response.EventResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiEndpoints : Service {

    @GET("events/")
    override fun getEvents(): Call<List<EventResponse>>

    @FormUrlEncoded
    @POST("checkin/")
    override fun checkIn(
        @Field("eventId") eventId: String?,
        @Field("name") name: String,
        @Field("email") email: String
    ): Call<Map<String, String>>
}