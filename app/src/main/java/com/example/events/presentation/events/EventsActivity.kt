package com.example.events.presentation.events

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.events.R
import com.example.events.data.repository.EventsApiDataSource
import com.example.events.presentation.base.BaseActivity
import com.example.events.presentation.details.EventDetailsActivity
import kotlinx.android.synthetic.main.activity_events.*
import kotlinx.android.synthetic.main.include_toolbar.*

class EventsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        setupToolbar(toolBarMain, R.string.events_title)

        val viewModel: EventsViewModel = EventsViewModel.ViewModelFactory(EventsApiDataSource())
            .create(EventsViewModel::class.java)

        viewModel.eventsLiveData.observe(this, Observer {
            it?.let { events ->
                with(recycleEvents) {
                    layoutManager =
                        LinearLayoutManager(this@EventsActivity, RecyclerView.VERTICAL, false)
                    setHasFixedSize(true)
                    adapter = EventsAdapter(events) { event ->
                        EventDetailsActivity.getStatIntent(
                            this@EventsActivity,
                            event.image,
                            event.title,
                            event.getFormatedDate(),
                            event.price,
                            event.description
                        )
                    }
                }
            }
        })

        viewModel.viewFlipperLiveData.observe(this, Observer { viewFlipper ->
            viewFlipperEvents.displayedChild = viewFlipper.first
            viewFlipper.second?.let { errorMessageResId ->
                textViewError.text = getString(errorMessageResId)
            }
        })

        viewModel.getEvents()
    }
}