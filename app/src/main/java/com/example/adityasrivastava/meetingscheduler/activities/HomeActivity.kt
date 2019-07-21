package com.example.adityasrivastava.meetingscheduler.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.adityasrivastava.meetingscheduler.R
import com.example.adityasrivastava.meetingscheduler.adapters.MeetingAdapter
import com.example.adityasrivastava.meetingscheduler.pojos.Meeting
import com.example.adityasrivastava.meetingscheduler.utils.UtilsMethods
import com.example.adityasrivastava.meetingscheduler.viewmodels.MeetingVM
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    var currentDate = ""
    lateinit var meetingVM: MeetingVM
    lateinit var mAdapter: MeetingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        currentDate = UtilsMethods.getTodayDate()

        mAdapter = MeetingAdapter(this)

        meetingVM = ViewModelProviders.of(this).get(MeetingVM::class.java)
        meetingVM.getMeetingsSchedule(this, currentDate).observe(this, observer)
    }

    /**
     * Observer
     */
    var observer = Observer<List<Meeting>> {

        if (it!=null){
            tv_no_meetings_scheduled.visibility = View.GONE
            mAdapter.setData(it as ArrayList<Meeting>)
        }else{
            tv_no_meetings_scheduled.visibility = View.VISIBLE
        }
    }
}
