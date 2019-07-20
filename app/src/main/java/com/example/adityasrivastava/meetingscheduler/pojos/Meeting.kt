package com.example.adityasrivastava.meetingscheduler.pojos

data class Meeting(
	val startTime: String? = null,
	val endTime: String? = null,
	val description: String? = null,
	val participants: List<String?>? = null
)
