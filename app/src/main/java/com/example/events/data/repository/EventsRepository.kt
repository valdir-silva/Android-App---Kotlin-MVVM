package com.example.events.data.repository

import com.example.events.data.ApiResults

interface EventsRepository {

    fun getEvents(eventsResultCallback: (result: ApiResults) -> Unit)

    fun checkIn(checkInResultCallback: (result: ApiResults) -> Unit)
}