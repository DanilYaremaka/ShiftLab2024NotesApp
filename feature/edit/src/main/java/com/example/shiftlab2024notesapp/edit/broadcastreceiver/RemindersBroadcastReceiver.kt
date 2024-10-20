package com.example.shiftlab2024notesapp.edit.broadcastreceiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.RingtoneManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.shiftlab2024notesapp.edit.R

class RemindersBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(context != null) {
            val text = intent?.getStringExtra("text") ?: "Reminder Text"
            val id = intent?.getIntExtra("id", 0)
            val notificationManager = NotificationManagerCompat.from(context)

            val channel = NotificationChannel(
                context.getString(R.string.reminder_chanel_id),
                context.getString(R.string.reminder_chanel_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = context.getString(R.string.reminder_description)
            }

            notificationManager.createNotificationChannel(channel)

            val builder = NotificationCompat.Builder(context, context.getString(R.string.reminder_chanel_id))
                .setSmallIcon(R.drawable.ic_reminder)
                .setContentTitle(context.resources.getString(R.string.new_reminder))
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))

            if(ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                return
            }
            notificationManager.notify(id!!, builder.build())
        }
    }
}