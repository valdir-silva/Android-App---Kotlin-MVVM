package com.example.events.data

import android.content.Context
import co.infinum.retromock.Retromock
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import com.example.events.BuildConfig
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiService {

    companion object {
        lateinit var apiEndpoints: ApiEndpoints
        lateinit var mockEndpoints: MockEndpoints

        fun init(context: Context) {

            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(client)
                .build()

            val retromock: Retromock = Retromock.Builder()
                .retrofit(retrofit)
                .defaultBodyFactory(context.assets::open)
                .build()


            apiEndpoints = retrofit.create(ApiEndpoints::class.java)
            mockEndpoints = retromock.create(MockEndpoints::class.java)
        }

//        val service: MockEndpoints = init().create(MockEndpoints::class.java)
    }
}