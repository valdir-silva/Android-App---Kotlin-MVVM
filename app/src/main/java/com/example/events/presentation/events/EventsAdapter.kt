package com.example.events.presentation.events

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.example.events.R
import com.example.events.data.model.EventModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_event.view.*

class EventsAdapter(
    private val eventModelList: List<EventModel>,
    private val onItemClickListener: ((event: EventModel) -> Unit)
) : RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, view: Int): EventsViewHolder {
        val eventItemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventsViewHolder(eventItemView, onItemClickListener)
    }

    override fun onBindViewHolder(viewHolder: EventsViewHolder, position: Int) {
        viewHolder.bindView(eventModelList[position])
    }

    override fun getItemCount() = eventModelList.count()

    class EventsViewHolder(
        eventItemView: View,
        private val onItemClickListener: ((event: EventModel) -> Unit)
    ) :
        RecyclerView.ViewHolder(eventItemView) {

        fun bindView(eventModel: EventModel) {
            Picasso.get()
                .load(eventModel.image)
                .into(itemView.imageEvent, object : Callback {
                    override fun onSuccess() {
                        Log.d(TAG, "success")
                    }

                    override fun onError(e: Exception?) {
                        itemView.imageEvent.setImageResource(R.drawable.default_image)
                        Log.d(TAG, "error: " + e?.message)
                    }
                })
            itemView.textTitle.text = eventModel.title
            itemView.textDate.text = eventModel.getFormatedDate()
            itemView.textPrice.text = "R$ " + eventModel.price.toString()

            itemView.setOnClickListener {
                onItemClickListener.invoke(eventModel)
            }
        }
    }
}