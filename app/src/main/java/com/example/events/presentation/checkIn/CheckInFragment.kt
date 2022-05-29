package com.example.events.presentation.checkIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.events.R
import com.example.events.data.repository.EventsApiDataSource
import com.example.events.presentation.details.EventDetailsViewModel
import kotlinx.android.synthetic.main.checkin_fragment.*

class CheckInFragment : Fragment() {

    companion object {
        fun newInstance() = CheckInFragment()
    }

    private val viewModel: EventDetailsViewModel = EventDetailsViewModel.ViewModelFactory(
        EventsApiDataSource()
    )
        .create(EventDetailsViewModel::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.checkin_fragment, container, false)
    }

    private fun registerListeners() {
        checkInButton.setOnClickListener {
            viewModel.checkIn()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerListeners()
    }

}