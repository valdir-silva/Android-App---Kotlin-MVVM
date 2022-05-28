package com.example.events

import android.app.Application
import com.example.events.data.ApiService

class App : Application() {


    override fun onCreate() {
        super.onCreate()

        ApiService.init(this)
    }

}