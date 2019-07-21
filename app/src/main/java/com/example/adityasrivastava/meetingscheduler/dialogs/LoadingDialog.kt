package com.example.adityasrivastava.meetingscheduler.dialogs

import android.app.Dialog
import android.content.Context

class LoadingDialog(context: Context) {
    var dialog: Dialog = Dialog(context)

    init {
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(R.layout.loading_dialog)
    }

    companion object {
        fun getInstance(context: Context): LoadingDialog {
            return LoadingDialog(context)
        }
    }

    /**
     * Show
     */
    fun show() {
        dialog.show()
    }

    /**
     * Dismiss
     */
    fun dismiss() {
        dialog.dismiss()
    }
}