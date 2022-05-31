package com.example.events.data

import com.example.events.data.model.EventModel

sealed class ApiResults {
    class Success(val eventModelList: List<EventModel>) : ApiResults()
    class ApiError(val statusCode: Int) : ApiResults()
    object ServerError : ApiResults()
}