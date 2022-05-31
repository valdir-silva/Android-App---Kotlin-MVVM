package com.example.events.presentation.events

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.events.R
import com.example.events.data.ApiResults
import com.example.events.data.model.EventModel
import com.example.events.data.repository.EventsRepository
import com.example.events.utils.toLiveData

class EventsViewModel(val dataSource: EventsRepository) : ViewModel() {

    private val _eventModelListLiveData: MutableLiveData<List<EventModel>> = MutableLiveData()
    val eventModelListLiveData = _eventModelListLiveData.toLiveData()
    private val _viewFlipperLiveData: MutableLiveData<Pair<Int, Int?>> = MutableLiveData()
    val viewFlipperLiveData = _viewFlipperLiveData.toLiveData()

    fun getEvents() {
        dataSource.getEvents { result: ApiResults ->
            when (result) {
                is ApiResults.Success -> {
                    _eventModelListLiveData.value = result.eventModelList
                    _viewFlipperLiveData.value = Pair(VIEW_FLIPPER_EVENTS, null)
                }
                is ApiResults.ApiError -> {
                    if (result.statusCode == 401) {
                        _viewFlipperLiveData.value =
                            Pair(VIEW_FLIPPER_ERROR, R.string.events_error_401)
                    } else {
                        _viewFlipperLiveData.value =
                            Pair(VIEW_FLIPPER_ERROR, R.string.events_error_400_generic)
                    }
                }
                is ApiResults.ServerError -> {
                    _viewFlipperLiveData.value =
                        Pair(VIEW_FLIPPER_ERROR, R.string.events_error_500_generic)
                }
            }
        }
    }

    class ViewModelFactory(val dataSource: EventsRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EventsViewModel::class.java)) {
                return EventsViewModel(dataSource) as T
            }
            throw IllegalArgumentException("Unknow ViewModel class")
        }
    }

    companion object {
        private const val VIEW_FLIPPER_EVENTS = 1
        private const val VIEW_FLIPPER_ERROR = 2
    }
}