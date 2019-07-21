package com.example.adityasrivastava.meetingscheduler.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.adityasrivastava.meetingscheduler.R
import androidx.recyclerview.widget.RecyclerView
import com.example.adityasrivastava.meetingscheduler.adapters.viewholders.MeetingVH
import com.example.adityasrivastava.meetingscheduler.pojos.Meeting
import com.example.adityasrivastava.meetingscheduler.utils.UtilsMethods
import kotlinx.android.synthetic.main.item_meeting.view.*

/*
 *Adapter
 */
class MeetingAdapter(val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /*
     *Variable Initialization
     */
    var list = ArrayList<Meeting>()

    /*
     *OnCreateViewHolder Method to inflate View
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MeetingVH(LayoutInflater.from(context).inflate(R.layout.item_meeting, parent, false))
    }

    /*
     *Method to get List Count
     */
    override fun getItemCount(): Int = list.size

    /*
     *Bind data to View
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val meetingObj = list.get(position)
        val meetingVH = holder as MeetingVH

        meetingVH.itemView.tv_time_slot.text = "${meetingObj.start_time?.let { UtilsMethods.convertTime24HourTo12Hour(it) }} - ${meetingObj.end_time?.let { UtilsMethods.convertTime24HourTo12Hour(it) }}"
        meetingVH.itemView.tv_description.text = meetingObj.description
    }

    /*
     *Populating list
     */
    fun setData(list: ArrayList<Meeting>){
        this.list = list
        notifyDataSetChanged()
    }
}