package com.example.events.presentation.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.events.R
import com.example.events.data.repository.EventsApiDataSource
import com.example.events.presentation.events.EventsViewModel
import kotlinx.android.synthetic.main.checkin_fragment.*

class EventDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = EventDetailsFragment()
    }

    private val viewModel: EventDetailsViewModel = EventDetailsViewModel.ViewModelFactory(EventsApiDataSource())
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