package com.example.events.presentation.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.events.R
import com.example.events.data.ApiResults
import com.example.events.data.model.EventRequest
import com.example.events.data.repository.EventsRepository
import com.example.events.utils.toLiveData

class EventDetailsViewModel(eventId: String, val dataSource: EventsRepository) :
    ViewModel() {

    private val _event: MutableLiveData<String> = MutableLiveData(eventId)

    private val _checkInResponseCodeLiveData: MutableLiveData<Pair<Int, Int?>> = MutableLiveData()
    val checkInResponseCodeLiveData = _checkInResponseCodeLiveData.toLiveData()

    fun checkIn(name: String, email: String) {
        dataSource.checkIn(
            EventRequest(
                _event.value.orEmpty(),
                name,
                email
            )
        ) { result: ApiResults ->
            when (result) {
                is ApiResults.Success -> {
                    _checkInResponseCodeLiveData.value = Pair(CHECK_IN_SUCCESS, null)
                }
                is ApiResults.ApiError -> {
                    if (result.statusCode == 401) {
                        _checkInResponseCodeLiveData.value =
                            Pair(CHECK_IN_ERROR, R.string.events_error_401)
                    } else {
                        _checkInResponseCodeLiveData.value =
                            Pair(CHECK_IN_ERROR, R.string.events_error_400_generic)
                    }
                }
                is ApiResults.ServerError -> {
                    _checkInResponseCodeLiveData.value =
                        Pair(CHECK_IN_ERROR, R.string.events_error_500_generic)
                }
            }
        }
    }

    class ViewModelFactory(val eventId: String, val dataSource: EventsRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EventDetailsViewModel::class.java)) {
                return EventDetailsViewModel(eventId = eventId, dataSource = dataSource) as T
            }
            throw IllegalArgumentException("Unknow ViewModel class")
        }
    }

    companion object {
        private const val CHECK_IN_SUCCESS = 1
        private const val CHECK_IN_ERROR = 2
    }
}