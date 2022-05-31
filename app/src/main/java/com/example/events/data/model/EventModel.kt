package com.example.events.data.model

import java.text.SimpleDateFormat
import java.util.*

data class EventModel(
//    val people: People,
    val date: Long,
    val description: String,
    val image: String,
    val price: Float,
    val title: String
) {
    fun getFormatedDate(): String {
        val date = Date(this.date)
        val format = SimpleDateFormat("dd/MM/yyy HH:mm")
        return format.format(date)
    }
}