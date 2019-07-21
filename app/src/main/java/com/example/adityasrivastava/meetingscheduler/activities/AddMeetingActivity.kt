package com.example.adityasrivastava.meetingscheduler.activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.adityasrivastava.meetingscheduler.R
import com.example.adityasrivastava.meetingscheduler.pojos.Meeting
import com.example.adityasrivastava.meetingscheduler.utils.UtilsMethods
import com.example.adityasrivastava.meetingscheduler.viewmodels.MeetingVM
import kotlinx.android.synthetic.main.activity_add_meeting.*
import kotlinx.android.synthetic.main.activity_home.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddMeetingActivity : AppCompatActivity() {

    lateinit var meetingVM: MeetingVM
    var listOfMeetings = arrayListOf<Meeting>()
    var isMeetingPossible = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_meeting)

        meetingVM = ViewModelProviders.of(this).get(MeetingVM::class.java)

        tv_meeting_date.setOnClickListener {
            showDatePicker()
        }

        tv_start_time.setOnClickListener {
            tv_start_time.pickTime()
        }

        tv_end_time.setOnClickListener {
            tv_end_time.pickTime()
        }

        btn_submit.setOnClickListener {
            if(UtilsMethods.checkSlotValidity(tv_start_time.text.toString(), tv_end_time.text.toString())){
                if(checkAvailableSlot()){
                    UtilsMethods.showMessage(this, resources.getString(R.string.slot_available), Toast.LENGTH_LONG)
                }else{
                    UtilsMethods.showMessage(this, resources.getString(R.string.slot_not_available), Toast.LENGTH_LONG)
                }
            }else{
                UtilsMethods.showMessage(this, resources.getString(R.string.start_time_before_end_time), Toast.LENGTH_LONG)
            }

        }

        btn_back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showDatePicker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // Display Selected date in Toast
            var calendar = Calendar.getInstance()
            calendar.set(year, monthOfYear, dayOfMonth)
            tv_meeting_date.text = dateFormat.format(calendar.time)
            btn_submit.isEnabled = !UtilsMethods.checkForPastDate(tv_meeting_date.text.toString())
            if(!btn_submit.isEnabled){
                UtilsMethods.showMessage(this, resources.getString(R.string.cannot_schedule_meeting_for_past_dates), Toast.LENGTH_LONG)
            }
            tv_start_time.text = resources.getString(R.string.start_time)
            tv_end_time.text = resources.getString(R.string.end_time)
            meetingVM.getMeetingsSchedule(this, UtilsMethods.getAPIDateFormat(tv_meeting_date.text.toString())).observe(this, observer)
        }, year, month, day).show()
    }

    private fun TextView.pickTime(){
        val calendar = Calendar.getInstance()
        TimePickerDialog(this@AddMeetingActivity, TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            val timeFormat = SimpleDateFormat("HH:mm aa", Locale.getDefault())
            this.text = timeFormat.format(calendar.time)
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
    }

    /**
     * Observer
     */
    var observer = Observer<List<Meeting>> {

        if (!it.isNullOrEmpty()){
            listOfMeetings = it as ArrayList<Meeting>
        }else{
            listOfMeetings.clear()
        }
    }

    private fun checkAvailableSlot(): Boolean {
        if(listOfMeetings.isNullOrEmpty()){
            return true
        }else{
            for(meeting in listOfMeetings){
                if(UtilsMethods.checkForOverlappingTime(meeting, tv_start_time.text.toString(), tv_end_time.text.toString())){
                    isMeetingPossible = false
                    break
                }
            }
            return isMeetingPossible
        }
    }
}
