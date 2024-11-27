package mx.aztek.hackernewsreader.utils

import mx.aztek.hackernewsreader.utils.Constants.CST_ZONE
import mx.aztek.hackernewsreader.utils.Constants.DATE_FORMAT
import mx.aztek.hackernewsreader.utils.Constants.DAY_MILLIS
import mx.aztek.hackernewsreader.utils.Constants.HOUR_MILLIS
import mx.aztek.hackernewsreader.utils.Constants.MINUTE_MILLIS
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

fun String.timeAgo(): String {
    val dateFormat = SimpleDateFormat(DATE_FORMAT)
    dateFormat.timeZone = TimeZone.getTimeZone(CST_ZONE)

    try {
        val convertedDate = dateFormat.parse(this)
        dateFormat.timeZone = TimeZone.getDefault()
        return getTimeAgo(convertedDate!!)
    } catch (e: ParseException) {
        return "Invalid string"
    }
}

fun getTimeAgo(date: Date): String {
    var time = date.time
    if (time < 1000000000000L) {
        time *= 1000
    }

    val now = currentDate().time
    if (time > now || time <= 0) {
        return "In the future"
    }

    val diff = now - time
    return when {
        diff < MINUTE_MILLIS -> "moments ago"
        diff < 2 * MINUTE_MILLIS -> "a minute ago"
        diff < 60 * MINUTE_MILLIS -> "${ diff / MINUTE_MILLIS } minutes ago"
        diff < 2 * HOUR_MILLIS -> "an hour ago"
        diff < 24 * HOUR_MILLIS -> "${ diff / HOUR_MILLIS } hours ago"
        diff < 48 * HOUR_MILLIS -> "yesterday"
        else -> "${ diff / DAY_MILLIS } days ago"
    }
}

private fun currentDate(): Date {
    val calendar = Calendar.getInstance()
    return calendar.time
}
