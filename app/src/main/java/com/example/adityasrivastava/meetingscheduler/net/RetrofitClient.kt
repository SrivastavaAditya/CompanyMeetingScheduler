package com.example.adityasrivastava.meetingscheduler.net

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    /*
     *Retrofit Interface
     */
    val retrofitInterface: RetrofitInterface
    /*
     *Base URL
     */
    private const val baseUrl = "http://fathomless-shelf-5846.herokuapp.com/api/"

    init {
        retrofitInterface = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(RetrofitInterface::class.java)
    }
}