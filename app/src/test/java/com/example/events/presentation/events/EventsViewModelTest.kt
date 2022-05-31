package com.example.events.presentation.events

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.events.R
import com.example.events.data.ApiResults
import com.example.events.data.model.EventModel
import com.example.events.data.repository.EventsRepository
import com.nhaarman.mockitokotlin2.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class EventsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var eventsLiveDataObserver: Observer<List<EventModel>>

    @Mock
    private lateinit var viewFlipperLiveDataObserver: Observer<Pair<Int, Int?>>

    private lateinit var viewModel: EventsViewModel

    @Test
    fun `when view model getEvents get success then sets eventsLiveData`() {
        // Arrange
        val events = listOf(
            EventModel(1534784400, "description 1", "http...", 1F, "Title 1")
        )
        val resultSuccess = MockRepository(ApiResults.Success(events))
        viewModel = EventsViewModel(resultSuccess)
        viewModel.eventModelListLiveData.observeForever(eventsLiveDataObserver)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        // Act
        viewModel.getEvents()

        // Assert
        verify(eventsLiveDataObserver).onChanged(events)
        verify(viewFlipperLiveDataObserver).onChanged(Pair(1, null))
    }

    @Test
    fun `when view model getEvents get api error then sets viewFlipperLiveData`() {
        // Arrange
        val resultApiError = MockRepository(ApiResults.ApiError(400))
        viewModel = EventsViewModel(resultApiError)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        // Act
        viewModel.getEvents()

        // Assert
        verify(viewFlipperLiveDataObserver).onChanged(Pair(2, R.string.events_error_400_generic))
    }

    @Test
    fun `when view model getEvents get server error then sets viewFlipperLiveData`() {
        // Arrange
        val resultServerError = MockRepository(ApiResults.ServerError)
        viewModel = EventsViewModel(resultServerError)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        // Act
        viewModel.getEvents()

        // Assert
        verify(viewFlipperLiveDataObserver).onChanged(Pair(2, R.string.events_error_500_generic))
    }
}

class MockRepository(private val result: ApiResults) : EventsRepository {
    override fun getEvents(eventsResultCallback: (result: ApiResults) -> Unit) {
        eventsResultCallback(result)
    }

    override fun checkIn(checkInResultCallback: (result: ApiResults) -> Unit) {
        TODO("Not yet implemented")
    }
}