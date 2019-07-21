package com.example.adityasrivastava.meetingscheduler.utils

import android.content.Context
import java.security.AccessControlContext
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object UtilsMethods {

    fun convertTime24HourTo12Hour(time: String): String{
        var time12Hour = time;
        val format24 = SimpleDateFormat("hh:mm", Locale.getDefault())
        val format12 = SimpleDateFormat("hh:mm aa", Locale.getDefault())
        try{
            val date = format24.parse(time)
            time12Hour = format12.format(date)
        }catch(e: ParseException){
            e.printStackTrace()
        }finally {
            return time12Hour
        }
    }

    fun getTodayDate(): String{
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return dateFormat.format(Calendar.getInstance().time)
    }

    fun getNextDate(currentDate: String): String{
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.time = dateFormat.parse(currentDate)
        calendar.add(Calendar.DATE, 1)
        return dateFormat.format(calendar.time)
    }

    fun getPreviousDate(currentDate: String): String{
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.time = dateFormat.parse(currentDate)
        calendar.add(Calendar.DATE, -1)
        return dateFormat.format(calendar.time)
    }
}