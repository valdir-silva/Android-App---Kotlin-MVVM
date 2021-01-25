package com.example.events.presentation.details

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import com.example.events.R
import com.example.events.presentation.base.BaseActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.event_details_activity.*
import kotlinx.android.synthetic.main.include_toolbar.*

class EventDetailsActivity : BaseActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_details_activity)
        setupToolbar(toolBarMain, R.string.event_details_title, true)

        Picasso.get()
            .load(intent.getStringExtra(EXTRA_IMAGE))
            .into(imageEventDetails, object : Callback {
                override fun onSuccess() {
                    Log.d("Picasso", "success")
                }

                override fun onError(e: Exception?) {
                    imageEventDetails.setImageResource(R.drawable.default_image)
                    Log.d("Picasso", "error: " + e?.message)
                }
            })
        eventDetailsTitle.text = intent.getStringExtra(EXTRA_TITLE)
        eventDetailsDate.text = intent.getStringExtra(EXTRA_DATE)
        eventDetailsPrice.text = "R$ " + intent.getStringExtra(EXTRA_PRICE)
        eventDetailsDescription.text = intent.getStringExtra(EXTRA_DESCRIPTION)

        registerListeners()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun registerListeners() {
        chekin_button.setOnClickListener {

            val eventDetailsFragment: EventDetailsFragment? =
                supportFragmentManager.findFragmentById(R.id.container) as EventDetailsFragment?

            when (eventDetailsFragment?.isVisible) {
                null -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, EventDetailsFragment.newInstance())
                        .commitNow()
                    chekin_button.text = resources.getText(R.string.fechar)
                    chekin_button.icon = getDrawable(R.drawable.ic_baseline_close_24)
                }
                true -> {
                    supportFragmentManager.beginTransaction()
                        .remove(eventDetailsFragment)
                        .commitNow()
                    chekin_button.text = resources.getText(R.string.chekIn)
                    chekin_button.icon = getDrawable(R.drawable.ic_baseline_place_24)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.overflowMenu -> {
                val myIntent = Intent(Intent.ACTION_SEND)
                myIntent.type = "text/plain"
                val shareSub = "Titulo Evento"
                val shareBody = "Detalhes Evento"
                myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub)
                myIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                startActivity(Intent.createChooser(myIntent, "Compartilhar"))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    companion object {
        private const val EXTRA_IMAGE = "EXTRA_IMAGE"
        private const val EXTRA_TITLE = "EXTRA_TITLE"
        private const val EXTRA_DATE = "EXTRA_DATE"
        private const val EXTRA_PRICE = "EXTRA_PRICE"
        private const val EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION"

        fun getStatIntent(
            contex: Context,
            image: String,
            title: String,
            date: String,
            price: Float,
            description: String
        ) {
            val intent = Intent(contex, EventDetailsActivity::class.java).apply {
                putExtra(EXTRA_IMAGE, image)
                putExtra(EXTRA_TITLE, title)
                putExtra(EXTRA_DATE, date)
                putExtra(EXTRA_PRICE, price.toString())
                putExtra(EXTRA_DESCRIPTION, description)
            }
            contex.startActivity(intent)
        }
    }
}