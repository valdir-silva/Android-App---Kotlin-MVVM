package com.example.events.presentation.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.events.R
import com.example.events.data.ApiResults
import com.example.events.data.model.Event
import com.example.events.data.repository.EventsRepository
import java.lang.IllegalArgumentException

class EventDetailsViewModel (val dataSource: EventsRepository) : ViewModel() {

    val checkInLiveData: MutableLiveData<List<Event>> = MutableLiveData()
    val checkInFlipperLiveData: MutableLiveData<Pair<Int, Int?>> = MutableLiveData()

    fun checkIn() {
        dataSource.checkIn { result: ApiResults ->
            when (result) {
                is ApiResults.Success -> {
                    checkInLiveData.value = result.events
                    checkInFlipperLiveData.value = Pair(VIEW_FLIPPER_EVENTS, null)
                }
                is ApiResults.ApiError -> {
                    if (result.statusCode == 401) {
                        checkInFlipperLiveData.value =
                            Pair(VIEW_FLIPPER_ERROR, R.string.events_error_401)
                    } else {
                        checkInFlipperLiveData.value =
                            Pair(VIEW_FLIPPER_ERROR, R.string.events_error_400_generic)
                    }
                }
                is ApiResults.ServerError -> {
                    checkInFlipperLiveData.value =
                        Pair(VIEW_FLIPPER_ERROR, R.string.events_error_500_generic)
                }
            }
        }
    }

    class ViewModelFactory(val dataSource: EventsRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EventDetailsViewModel::class.java)) {
                return EventDetailsViewModel(dataSource) as T
            }
            throw IllegalArgumentException("Unknow ViewModel class")
        }
    }

    companion object {
        private const val VIEW_FLIPPER_EVENTS = 1
        private const val VIEW_FLIPPER_ERROR = 2
    }
}