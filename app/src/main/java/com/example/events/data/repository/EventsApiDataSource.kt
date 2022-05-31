package com.example.events.data.repository

import com.example.events.BuildConfig
import com.example.events.data.ApiResults
import com.example.events.data.ApiService
import com.example.events.data.Service
import com.example.events.data.model.EventModel
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

    override fun checkIn(checkInResultCallback: (result: ApiResults) -> Unit) {
        service.checkIn("1", "abc", "abc@abc.com").enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                when {
                    response.isSuccessful -> {
                        val result: MutableList<EventModel> = mutableListOf()
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