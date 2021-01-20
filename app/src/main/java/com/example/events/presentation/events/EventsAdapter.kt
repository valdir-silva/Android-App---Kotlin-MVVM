package com.example.events.presentation.events

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import com.example.events.R
import com.example.events.data.model.Event
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_event.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class EventsAdapter(
    private val events: List<Event>,
    val onItemClickListener: ((event: Event) -> Unit)
) : RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, view: Int): EventsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventsViewHolder(itemView, onItemClickListener)
    }

    override fun onBindViewHolder(viewHolder: EventsViewHolder, position: Int) {
        viewHolder.bindView(events[position])
    }

    override fun getItemCount() = events.count()

    class EventsViewHolder(itemView: View, val onItemClickListener: ((event: Event) -> Unit)) :
        RecyclerView.ViewHolder(itemView) {

        private val image = itemView.imageEvent
        private val title = itemView.textTitle
        private val date = itemView.textDate
        private val price = itemView.textPrice

        fun bindView(event: Event) {
            Picasso.get()
                .load(event.image)
                .into(image, object : Callback {
                    override fun onSuccess() {
                        Log.d(TAG, "success")
                    }
                    override fun onError(e: Exception?) {
                        image.setImageResource(R.drawable.default_image)
                        Log.d(TAG, "error: " + e?.message)
                    }
                })
            title.text = event.title
            date.text = event.getFormatedDate()
            price.text = "R$ " + event.price.toString()

            itemView.setOnClickListener {
                onItemClickListener.invoke(event)
            }
        }
    }
}