package com.example.events.presentation.checkIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        eventId = "123",
        dataSource = EventsApiDataSource()
    )
        .create(EventDetailsViewModel::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.getString("eventId").orEmpty()
        return inflater.inflate(R.layout.checkin_fragment, container, false)
    }

    private fun registerListeners() {
        checkInButton.setOnClickListener {
            viewModel.checkIn(
                name = textNameCheckIn.text.toString(),
                email = textEmailCheckIn.text.toString()
            )
        }
        viewModel.checkInResponseCodeLiveData.observe(this) { response ->
            if (response.second == null) {
                Toast.makeText(context, "Chechin realizado com sucesso!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Error: ${response.second}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerListeners()
    }

}