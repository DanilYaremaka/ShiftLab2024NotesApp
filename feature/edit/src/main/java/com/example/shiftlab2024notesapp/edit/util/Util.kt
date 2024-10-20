package com.example.shiftlab2024notesapp.edit.util

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import com.example.shiftlab2024notesapp.edit.broadcastreceiver.RemindersBroadcastReceiver
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
object FutureOrPresentSelectableDates: SelectableDates {
    @ExperimentalMaterial3Api
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        val cal = Calendar.getInstance()
        cal.timeInMillis = System.currentTimeMillis()
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return utcTimeMillis >= cal.timeInMillis
    }

    @ExperimentalMaterial3Api
    override fun isSelectableYear(year: Int): Boolean {
        return year >= LocalDate.now().year
    }
}

fun dateToString(long: Long?): String {
    val sdf = SimpleDateFormat("dd MMM HH:mm", Locale.getDefault())
    return if (long == null) ""
    else sdf.format(Date(long))
}

fun getPendingIntent(context: Context, id: Int, text: String): PendingIntent {
    val intent = Intent(context, RemindersBroadcastReceiver::class.java).apply {
        putExtra("text", text)
        putExtra("id", id)
    }
    return PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT
            or PendingIntent.FLAG_IMMUTABLE)
}