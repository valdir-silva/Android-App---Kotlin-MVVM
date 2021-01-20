package com.example.events.data

import com.example.events.data.model.Event

sealed class ApiResults {
    class Success(val events: List<Event>) : ApiResults()
    class ApiError(val statusCode: Int) : ApiResults()
    object ServerError : ApiResults()
}