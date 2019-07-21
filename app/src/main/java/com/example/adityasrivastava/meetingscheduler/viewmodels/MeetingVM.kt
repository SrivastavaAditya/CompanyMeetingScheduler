package com.example.adityasrivastava.meetingscheduler.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.adityasrivastava.meetingscheduler.dialogs.LoadingDialog
import com.example.adityasrivastava.meetingscheduler.net.RetrofitClient
import com.example.adityasrivastava.meetingscheduler.pojos.Meeting
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MeetingVM(application: Application): AndroidViewModel(application) {

    fun getMeetingsSchedule(context: Context, date: String): MutableLiveData<List<Meeting>>{
        val loadingDialog = LoadingDialog(context)
        loadingDialog.show()

        val mutableLiveData = MutableLiveData<List<Meeting>>()
        val disposable = RetrofitClient.retrofitInterface.getMeetingSchedules(date).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                mutableLiveData.value = it
                loadingDialog.dismiss()
            }, {
                loadingDialog.dismiss()
                mutableLiveData.value = null
                Log.e("Error", it.toString())
                Toast.makeText(this@MeetingVM.getApplication(), "Network Error", Toast.LENGTH_LONG).show()
            })
        return mutableLiveData
    }
}