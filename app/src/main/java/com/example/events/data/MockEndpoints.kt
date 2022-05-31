package com.example.events.data

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockResponse
import com.example.events.data.response.EventResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface MockEndpoints : Service {

    @MockResponse(body = "events.json")
    @Mock
    @GET("/")
    override fun getEvents(): Call<List<EventResponse>>

    @MockResponse(body = "checkin.json")
    @Mock
    @FormUrlEncoded
    @POST("/")
    override fun checkIn(
        @Field("eventId") eventId: String?,
        @Field("name") name: String,
        @Field("email") email: String
    ): Call<Map<String, String>>

//    fun checkIn(@Body checkin: Checkin): Call<EventApiResult>
}