package com.example.adityasrivastava.meetingscheduler.utils

import android.content.Context
import android.widget.Toast
import androidx.loader.app.LoaderManager
import com.example.adityasrivastava.meetingscheduler.pojos.Meeting
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object UtilsMethods {

    fun convertTime24HourTo12Hour(time: String): String{
        var time12Hour = time
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

    fun checkSlotValidity(startTime: String, endTime: String): Boolean{
        val timeFormat = SimpleDateFormat("hh:mm", Locale.getDefault())
        return timeFormat.parse(startTime).before(timeFormat.parse(endTime))
    }

    fun checkForOverlappingTime(meeting: Meeting, startTime: String, endTime: String): Boolean{
        val timeFormat = SimpleDateFormat("hh:mm", Locale.getDefault())
        val mStartTime = timeFormat.parse(meeting.start_time)
        val mEndTime = timeFormat.parse(meeting.end_time)

        val tempStartTime = timeFormat.parse(startTime)
        val tempEndTime = timeFormat.parse(endTime)

        if(timeFormat.format(tempStartTime).equals(meeting.start_time) || timeFormat.format(tempEndTime).equals(meeting.end_time)){
            return true
        }

        if((tempStartTime.after(mStartTime) && tempStartTime.before(mEndTime))){
            return true
        }else if(tempEndTime.after(mStartTime) && tempEndTime.before(mEndTime)){
            return true
        }

        if(tempStartTime.before(mStartTime) && tempEndTime.after(mEndTime)){
            return true
        }
        return false
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

    fun getAPIDateFormat(dateStr: String):String{
        val localFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val apiFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        var apiDate = dateStr
        try{
            val date = localFormat.parse(dateStr)
            apiDate = apiFormat.format(date)
        }catch (e: ParseException){
            e.printStackTrace()
        }finally {
            return apiDate
        }
    }

    fun checkForPastDate(dateStr: String): Boolean{
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val date = dateFormat.parse(dateStr)
        if(date.before(dateFormat.parse(getTodayDate()))){
            return true
        }
        return false
    }

    fun showMessage(context: Context, message: String, timeDuration: Int){
        Toast.makeText(context, message, timeDuration).show()
    }
}