package com.example.events.data.repository

import com.example.events.data.ApiResults
import com.example.events.data.model.EventRequest

interface EventsRepository {

    fun getEvents(eventsResultCallback: (result: ApiResults) -> Unit)

    fun checkIn(eventRequest: EventRequest, checkInResultCallback: (result: ApiResults) -> Unit)
}