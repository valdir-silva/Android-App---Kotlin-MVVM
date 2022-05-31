package com.example.events.data.repository

import com.example.events.BuildConfig
import com.example.events.data.ApiResults
import com.example.events.data.ApiService
import com.example.events.data.Service
import com.example.events.data.model.EventModel
import com.example.events.data.model.EventRequest
import com.example.events.data.response.EventResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventsApiDataSource : EventsRepository {

    val service: Service =
        if (BuildConfig.BUILD_TYPE == "mock") ApiService.mockEndpoints else ApiService.apiEndpoints

    override fun getEvents(eventsResultCallback: (result: ApiResults) -> Unit) {
        service.getEvents().enqueue(object : Callback<List<EventResponse>> {
            override fun onResponse(
                call: Call<List<EventResponse>>,
                response: Response<List<EventResponse>>
            ) {
                when {
                    response.isSuccessful -> {
                        val eventModelList: MutableList<EventModel> = mutableListOf()

                        response.body()?.let { eventBodyResponse ->
                            for (result in eventBodyResponse) {
                                val eventModel = result.getEventModel()
                                eventModelList.add(eventModel)
                            }
                        }

                        eventsResultCallback(ApiResults.Success(eventModelList))
                    }
                    else -> eventsResultCallback(ApiResults.ApiError(response.code()))
                }
            }

            override fun onFailure(call: Call<List<EventResponse>>, t: Throwable) {
                eventsResultCallback(ApiResults.ServerError)
            }
        })
    }

    override fun checkIn(
        eventRequest: EventRequest,
        checkInResultCallback: (result: ApiResults) -> Unit
    ) {
        service.checkIn(eventRequest.eventId, eventRequest.name, eventRequest.email)
            .enqueue(object : Callback<Map<String, String>> {
                override fun onResponse(
                    call: Call<Map<String, String>>,
                    response: Response<Map<String, String>>
                ) {
                    when {
                        response.isSuccessful -> {
                            // TODO Melhorar abstracao do Success ou já mudar tudo para coroutines
                            checkInResultCallback(ApiResults.Success(listOf()))
                        }
                        else -> checkInResultCallback(ApiResults.ApiError(response.code()))
                    }
                }

                override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
                    checkInResultCallback(ApiResults.ServerError)
                }

            })
    }

}