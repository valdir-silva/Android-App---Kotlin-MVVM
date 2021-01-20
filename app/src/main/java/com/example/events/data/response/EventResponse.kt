package com.example.events.data.response

import com.example.events.data.model.Event
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class EventResponse(
//    @Json(name = "people")
//    val List<People>: List<People>,
    @Json(name = "date")
    val date: Long,
    @Json(name = "description")
    val description: String,
    @Json(name = "image")
    val image: String,
    @Json(name = "longitude")
    val longitude: Double,
    @Json(name = "latitude")
    val latitude: Double,
    @Json(name = "price")
    val price: Float,
    @Json(name = "title")
    val title: String,
    @Json(name = "id")
    val id: String,
) {
    fun getEventModel() = Event(
//        people = this.people
        date = this.date,
        description = this.description,
        image = this.image,
        price = this.price,
        title = this.title
    )
}