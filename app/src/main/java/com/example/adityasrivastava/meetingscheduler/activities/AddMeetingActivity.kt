package com.example.adityasrivastava.meetingscheduler.activities

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.adityasrivastava.meetingscheduler.R
import com.example.adityasrivastava.meetingscheduler.utils.UtilsMethods
import kotlinx.android.synthetic.main.activity_add_meeting.*
import java.text.SimpleDateFormat
import java.util.*

class AddMeetingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_meeting)

        tv_meeting_date.setOnClickListener {
            showDatePicker()
        }

        tv_start_time.setOnClickListener {

        }

        tv_end_time.setOnClickListener {

        }

        btn_submit.setOnClickListener {
            if(checkAvailableSlot()){
                UtilsMethods.showMessage(this, resources.getString(R.string.slot_available), Toast.LENGTH_LONG)
            }else{
                UtilsMethods.showMessage(this, resources.getString(R.string.slot_not_available), Toast.LENGTH_LONG)
            }
        }
    }

    private fun showDatePicker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // Display Selected date in Toast
            var calendar = Calendar.getInstance()
            calendar.set(year, monthOfYear, dayOfMonth)
            tv_meeting_date.text = dateFormat.format(calendar.time)
        }, year, month, day)
        dpd.show()
    }

    private fun checkAvailableSlot(): Boolean {
        return true
    }
}
