package com.example.events.data

import com.example.events.data.response.EventResponse
import retrofit2.Call
import retrofit2.http.*

interface MockapiServices {

    @GET("events/")
    fun getEvents(): Call<List<EventResponse>>

    @FormUrlEncoded
    @POST("checkin/")
    fun checkIn(
        @Field("eventId") eventId: String? = null,
        @Field("name") name: String,
        @Field("email") email: String
    ): Call<Boolean>

//    fun checkIn(@Body checkin: Checkin): Call<EventApiResult>
}