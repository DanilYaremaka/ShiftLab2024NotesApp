package com.example.shiftlab2024notesapp.edit.ui

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shiftlab2024notesapp.edit.R
import com.example.shiftlab2024notesapp.edit.util.FutureOrPresentSelectableDates
import com.example.shiftlab2024notesapp.edit.util.dateToString
import com.example.shiftlab2024notesapp.shared.entity.Note
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentComponent(
    note: Note,
    onTitleChanged: (String) -> Unit,
    onTextChanged: (String) -> Unit,
    onSaveClicked: (Context, ActivityResultLauncher<String>) -> Unit,
    onBackClicked: () -> Unit,
    onFavouriteClicked: () -> Unit,
    reminderDateChanged: (Long) -> Unit,
    reminderRemoved: (Context) -> Unit,
) {
    val context = LocalContext.current

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    val notificationLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            showDatePicker = isGranted
        }
    )

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Date().time,
        selectableDates = FutureOrPresentSelectableDates
    )

    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { onBackClicked() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (note.id != null) {
                            if (note.reminderDate != null)
                                reminderRemoved(context)
                            else {
                                showDatePicker = true
                            }
                        }
                    }) {
                        Icon(
                            imageVector = if (note.reminderDate != null) Icons.Filled.Notifications
                            else Icons.Outlined.Notifications,
                            contentDescription = null
                        )
                    }
                    IconButton(onClick = { onFavouriteClicked() }) {
                        Icon(
                            imageVector = if (note.isFavourite) Icons.Filled.Favorite
                            else Icons.Outlined.FavoriteBorder,
                            contentDescription = null
                        )
                    }
                    IconButton(onClick = { onSaveClicked(context, notificationLauncher) }) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = null
                        )
                    }
                }
            )
        }

    ) { paddingValues ->

        if (showDatePicker) {
            DatePickerModal(
                onDateSelected = { dateInMillis ->
                    if (dateInMillis != null) {
                        showDatePicker = false
                        showTimePicker = true
                    }
                },
                onDismiss = {
                    showDatePicker = false
                },
                datePickerState = datePickerState
            )
        }
        else if (showTimePicker) {
            TimePickerModal(
                onTimeSelected = {
                    showTimePicker = false
                    val cal = Calendar.getInstance()
                    cal.timeInMillis = datePickerState.selectedDateMillis ?: 0L
                    cal.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                    cal.set(Calendar.MINUTE, timePickerState.minute)
                    cal.set(Calendar.SECOND, 0)
                    cal.set(Calendar.MILLISECOND, 0)

                    reminderDateChanged(cal.timeInMillis)
                },
                onDismiss = {
                    showTimePicker = false
                },
                timePickerState = timePickerState
            )
        }
        else {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {

                NoteTitle(
                    noteTitle = note.title,
                    onTitleChanged = onTitleChanged
                )
                Spacer(modifier = Modifier.height(8.dp))

                NoteInfo(
                    charCount = note.text.length,
                    lastUpdate = note.lastUpdate,
                    reminderDate = note.reminderDate
                )

                Spacer(modifier = Modifier.height(8.dp))
                NoteText(
                    noteText = note.text,
                    onTextChanged = onTextChanged
                )
            }
        }
    }

    BackHandler {
        onBackClicked()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    datePickerState: DatePickerState,
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
) {

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text(stringResource(R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            showModeToggle = false,
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TimePickerModal(
    onTimeSelected: () -> Unit,
    onDismiss: () -> Unit,
    timePickerState: TimePickerState
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TimePicker(
                state = timePickerState,
            )
            Button(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
            Button(onClick = { onTimeSelected() }) {
                Text(stringResource(R.string.confirm))
            }
        }
    }

}

@Composable
fun NoteTitle(
    noteTitle: String,
    onTitleChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = noteTitle,
        onValueChange = onTitleChanged,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 16.dp),
        placeholder = {
            Text(text = stringResource(R.string.title_hint))
        },
        textStyle = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )
    )
}

@Composable
fun NoteInfo(
    charCount: Int,
    lastUpdate: Long?,
    reminderDate: Long?
) {

    val updateString = dateToString(lastUpdate).takeIf { it.isNotBlank() }?.plus(" | ") ?: ""
    val reminderString = dateToString(reminderDate).takeIf { it.isNotBlank() }?.plus(" ") ?: ""

    val iconId = R.drawable.ic_reminder_text

    val annotatedString = buildAnnotatedString {
        append(updateString)
        append(
            pluralStringResource(
                id = R.plurals.chars_count,
                count = charCount,
                charCount
            )
        )
        if (reminderString.isNotBlank()) {
            append(" | ")
            appendInlineContent("reminderIcon", "[icon]")
            append(" ")
            append(reminderString)
        }
    }

    val inlineContent = mapOf(
        "reminderIcon" to InlineTextContent(
            placeholder = Placeholder(
                width = 16.sp,
                height = 16.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center
            )
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        }
    )
    Text(
        text = annotatedString,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        fontSize = 12.sp,
        fontWeight = FontWeight.Light,
        inlineContent = inlineContent
    )

}

@Composable
fun NoteText(
    noteText: String,
    onTextChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = noteText,
        onValueChange = onTextChanged,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp),
        placeholder = {
            Text(text = stringResource(R.string.text))
        },
        textStyle = TextStyle(
            fontWeight = FontWeight.Normal
        )
    )
}