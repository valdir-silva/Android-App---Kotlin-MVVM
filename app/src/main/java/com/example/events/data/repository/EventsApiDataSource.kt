package com.example.events.data.repository

import com.example.events.data.ApiService
import com.example.events.data.ApiResults
import com.example.events.data.model.Event
import com.example.events.data.response.EventResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventsApiDataSource : EventsRepository {

    override fun getEvents(eventsResultCallback: (result: ApiResults) -> Unit) {
        ApiService.service.getEvents().enqueue(object : Callback<List<EventResponse>> {
            override fun onResponse(
                call: Call<List<EventResponse>>,
                response: Response<List<EventResponse>>
            ) {
                when {
                    response.isSuccessful -> {
                        val events: MutableList<Event> = mutableListOf()

                        response.body()?.let { eventBodyResponse ->
                            for (result in eventBodyResponse) {
                                val event = result.getEventModel()
                                events.add(event)
                            }
                        }

                        eventsResultCallback(ApiResults.Success(events))
                    }
                    else -> eventsResultCallback(ApiResults.ApiError(response.code()))
                }
            }

            override fun onFailure(call: Call<List<EventResponse>>, t: Throwable) {
                eventsResultCallback(ApiResults.ServerError)
            }
        })
    }

    override fun checkIn(checkInResultCallback: (result: ApiResults) -> Unit) {
        ApiService.service.checkIn("1", "abc", "abc@abc.com").enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                when {
                    response.isSuccessful -> {
                        val result: MutableList<Event> = mutableListOf()
                    }
                    else -> checkInResultCallback(ApiResults.ApiError(response.code()))
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                checkInResultCallback(ApiResults.ServerError)
            }

        })
    }

}