package com.example.shiftlab2024notesapp.edit.presentation

import android.Manifest
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shiftlab2024notesapp.edit.domain.usecase.InsertNoteUseCase
import com.example.shiftlab2024notesapp.edit.util.getCurrentTimeInMillis
import com.example.shiftlab2024notesapp.edit.util.getPendingIntent
import com.example.shiftlab2024notesapp.shared.entity.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class EditViewModel(
    private val note: Note,
    private val insertNoteUseCase: InsertNoteUseCase,
    private val router: EditRouter,
) : ViewModel() {

    private val _state = MutableStateFlow<EditState>(EditState.Initial)
    val state: StateFlow<EditState> = _state

    private val title = mutableStateOf(note.title)
    private val text = mutableStateOf(note.text)
    private val isFavourite = mutableStateOf(note.isFavourite)
    private val reminderTime = mutableStateOf(note.reminderDate)

    private var isNotificationPermissionGranted by mutableStateOf(false)
    private var isExactAlarmPermissionGranted by mutableStateOf(false)

    init {
        getCurrentReminder()
    }

    private fun getCurrentReminder() {
        val reminder = if (note.reminderDate == null)
            null
        else {
            if (note.reminderDate!! < getCurrentTimeInMillis())
                null
            else note.reminderDate
        }
        reminderTime.value = reminder
    }

    private fun updateReminderTitle(context: Context) {
        if (note.title == title.value)
            return
        removeReminderAlarm(context)
        scheduleReminder(context)
    }

    fun changeTitle(value: String) {
        title.value = value
        showNote()
    }

    fun changeFavouriteState() {
        isFavourite.value = !isFavourite.value
        showNote()
    }

    fun changeText(value: String) {
        text.value = value
        showNote()
    }

    fun showNote() {
        val permissionsGranted = isNotificationPermissionGranted && isExactAlarmPermissionGranted
        _state.value = EditState.Content(
            note.copy(
                title = title.value,
                text = text.value,
                isFavourite = isFavourite.value,
                reminderDate = reminderTime.value
            ),
            permissionsGranted
        )
    }

    fun setNote(context: Context, notificationLauncher: ActivityResultLauncher<String>) {
        val state = state.value
        if (state !is EditState.Content) return

        if (notValidated()) return

        if (reminderTime.value != null && note.reminderDate == null) {
            requestPermissions(context, notificationLauncher)
            if (isExactAlarmPermissionGranted && isNotificationPermissionGranted)
                scheduleReminder(context)
        }

        updateReminderTitle(context)

        insertNote()
    }

    fun insertNote() {
        viewModelScope.launch {
            try {
                insertNoteUseCase(
                    note.copy(
                        text = text.value,
                        title = title.value,
                        isFavourite = isFavourite.value,
                        lastUpdate = getCurrentTimeInMillis(),
                        reminderDate = reminderTime.value
                    )
                )
                closeNote()
            } catch (ce: CancellationException) {
                throw ce
            } catch (ex: Exception) {
                _state.value = EditState.Failure(ex.localizedMessage.orEmpty())
            }
        }
    }

    fun createReminder(newValue: Long) {
        if (note.id == null || newValue <= getCurrentTimeInMillis())
            return

        reminderTime.value = newValue
        showNote()
    }

    fun removeReminder(context: Context) {
        reminderTime.value = null
        removeReminderAlarm(context)
        showNote()
    }

    private fun removeReminderAlarm(context: Context) {
        if (note.reminderDate != null) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(getPendingIntent(context, note.id!!, note.title))
        }
    }

    fun requestPermissions(context: Context, notificationLauncher: ActivityResultLauncher<String>) {
        isNotificationPermissionGranted = true
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (!alarmManager.canScheduleExactAlarms()) {
            requestExactAlarmPermission(context, alarmManager)
        } else isExactAlarmPermissionGranted = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                notificationLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                isNotificationPermissionGranted = true
            }
        } else {
            isNotificationPermissionGranted = true
        }
        showNote()
    }

    private fun requestExactAlarmPermission(context: Context, alarmManager: AlarmManager) {
        if (!alarmManager.canScheduleExactAlarms()) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            context.startActivity(intent)
        } else {
            isExactAlarmPermissionGranted = true
        }
    }

    private fun scheduleReminder(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            reminderTime.value!!,
            getPendingIntent(context, note.id!!, title.value)
        )
    }


    private fun notValidated(): Boolean {
        return (text.value == "" && title.value == "")
    }

    private fun closeNote() {
        router.saveAndClose()
    }

    fun closeDraft() {
        if (note.id == null)
            router.close()
        else closeNote()
    }
}