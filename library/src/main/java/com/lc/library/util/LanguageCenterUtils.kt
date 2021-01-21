package com.lc.library.util

import java.text.SimpleDateFormat
import java.util.*

object LanguageCenterUtils {

    fun getCurrentTimeMillis(): Long {
        return System.currentTimeMillis()
    }

    fun getRandomUUID(): String {
        return UUID.randomUUID().toString().replace("-", "")
    }

    fun getDateFormat(dateTime: Long): String {
        val dateSdf = SimpleDateFormat("E, MMM d", Locale.getDefault())
        return dateSdf.format(dateTime)
    }

    fun getTimeFormat(dateTime: Long): String {
        val timeSdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return timeSdf.format(dateTime)
    }

    fun getDateTimeFormat(dateTime: Long): String {
        val dateTimeSdf = SimpleDateFormat("E, d MMM yyyy HH:mm", Locale.getDefault())
        return dateTimeSdf.format(dateTime)
    }

}
