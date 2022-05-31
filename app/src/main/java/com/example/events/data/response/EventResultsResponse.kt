package com.example.events.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EventResultsResponse (
    @Json(name = "title")
    val title: String,
    @Json(name = "description")
    val description: String
)