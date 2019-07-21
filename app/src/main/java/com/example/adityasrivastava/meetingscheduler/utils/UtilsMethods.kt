package com.example.adityasrivastava.meetingscheduler.utils

import android.content.Context
import android.widget.Toast
import androidx.loader.app.LoaderManager
import com.example.adityasrivastava.meetingscheduler.pojos.Meeting
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object UtilsMethods {

    /*
     *Convert 24 hour format time from API to 12 hour format
     *returns time in 12 hour format
     */
    fun convertTime24HourTo12Hour(time: String): String{
        var time12Hour = time
        val format24 = SimpleDateFormat("HH:mm", Locale.getDefault())
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

    /*
     *Check if Starting time is before Ending Time
     *returns true if start time is before end time and vice-versa
     */
    fun checkSlotValidity(startTime: String, endTime: String): Boolean{
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return timeFormat.parse(startTime).before(timeFormat.parse(endTime))
    }

    /*
     *Check if selected slots overlap already scheduled meeting slot
     *returns true if overlap occurs and vice-versa
     */
    fun checkForOverlappingTime(meeting: Meeting, startTime: String, endTime: String): Boolean{
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val mStartTime = timeFormat.parse(meeting.start_time)
        val mEndTime = timeFormat.parse(meeting.end_time)

        val tempStartTime = timeFormat.parse(startTime)
        val tempEndTime = timeFormat.parse(endTime)

        //overlap when selected slots and meeting slots are same
        if(timeFormat.format(tempStartTime).equals(meeting.start_time) || timeFormat.format(tempEndTime).equals(meeting.end_time)){
            return true
        }

        //overlap when either selected starting time or selected ending time or both lie in between meeting slots
        if((tempStartTime.after(mStartTime) && tempStartTime.before(mEndTime))){
            return true
        }else if(tempEndTime.after(mStartTime) && tempEndTime.before(mEndTime)){
            return true
        }

        //overlap when both selected time slot encloses the meeting slot
        if(tempStartTime.before(mStartTime) && tempEndTime.after(mEndTime)){
            return true
        }
        return false
    }

    /*
     *Get Todays Date
     *returns todays date
     */
    fun getTodayDate(): String{
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return dateFormat.format(Calendar.getInstance().time)
    }

    /*
     *Get Next Date
     *returns next date from given current date
     */
    fun getNextDate(currentDate: String): String{
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.time = dateFormat.parse(currentDate)
        calendar.add(Calendar.DATE, 1)
        return dateFormat.format(calendar.time)
    }

    /*
     *Get previous date
     *returns previous date from current date
     */
    fun getPreviousDate(currentDate: String): String{
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.time = dateFormat.parse(currentDate)
        calendar.add(Calendar.DATE, -1)
        return dateFormat.format(calendar.time)
    }

    /*
     *Get date in format to send in API
     *returns date in dd/MM/yyyy format for API
     */
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

    /*
     *Check if selected date is a past date
     *returns true if given date is past date
     */
    fun checkForPastDate(dateStr: String): Boolean{
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val date = dateFormat.parse(dateStr)
        if(date.before(dateFormat.parse(getTodayDate()))){
            return true
        }
        return false
    }

    /*
     *Show Toast Message
     */
    fun showMessage(context: Context, message: String, timeDuration: Int){
        Toast.makeText(context, message, timeDuration).show()
    }
}