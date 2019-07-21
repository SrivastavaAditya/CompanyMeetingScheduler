package com.example.adityasrivastava.meetingscheduler.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adityasrivastava.meetingscheduler.R
import com.example.adityasrivastava.meetingscheduler.adapters.MeetingAdapter
import com.example.adityasrivastava.meetingscheduler.pojos.Meeting
import com.example.adityasrivastava.meetingscheduler.utils.UtilsMethods
import com.example.adityasrivastava.meetingscheduler.viewmodels.MeetingVM
import kotlinx.android.synthetic.main.activity_home.*

/*
 *Activity
 */
class HomeActivity : AppCompatActivity() {

    /*
     *Variable Initialization
     */
    var currentDate = ""
    lateinit var meetingVM: MeetingVM
    lateinit var mAdapter: MeetingAdapter

    /*
     *OnCreate Callback
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        currentDate = UtilsMethods.getTodayDate()
        tv_date.text = currentDate

        mAdapter = MeetingAdapter(this)

        rv_meetings.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = mAdapter
        }

        meetingVM = ViewModelProviders.of(this).get(MeetingVM::class.java)
        meetingVM.getMeetingsSchedule(this, UtilsMethods.getAPIDateFormat(currentDate)).observe(this, observer)

        btn_next.setOnClickListener {
            currentDate = UtilsMethods.getNextDate(currentDate)
            tv_date.text = currentDate
            btn_add_meeting.isEnabled = !UtilsMethods.checkForPastDate(currentDate)
            meetingVM.getMeetingsSchedule(this, UtilsMethods.getAPIDateFormat(currentDate)).observe(this, observer)
        }

        btn_prev.setOnClickListener {
            currentDate = UtilsMethods.getPreviousDate(currentDate)
            tv_date.text = currentDate
            btn_add_meeting.isEnabled = !UtilsMethods.checkForPastDate(currentDate)
            meetingVM.getMeetingsSchedule(this, UtilsMethods.getAPIDateFormat(currentDate)).observe(this, observer)
        }

        btn_add_meeting.setOnClickListener {
            val intent = Intent(this, AddMeetingActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Observer
     */
    var observer = Observer<List<Meeting>> {

        if (!it.isNullOrEmpty()){
            tv_no_meetings_scheduled.visibility = View.GONE
            rv_meetings.visibility = View.VISIBLE
            mAdapter.setData(it as ArrayList<Meeting>)
        }else{
            tv_no_meetings_scheduled.visibility = View.VISIBLE
            rv_meetings.visibility = View.INVISIBLE
        }
    }
}