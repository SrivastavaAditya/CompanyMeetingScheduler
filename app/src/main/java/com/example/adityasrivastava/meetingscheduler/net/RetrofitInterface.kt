package com.example.adityasrivastava.meetingscheduler.net

import com.example.adityasrivastava.meetingscheduler.pojos.Meeting
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit Interface
 */
interface RetrofitInterface {
    @GET("schedule")
    fun getMeetingSchedules(@Query("date") date: String): Observable<List<Meeting>>
}