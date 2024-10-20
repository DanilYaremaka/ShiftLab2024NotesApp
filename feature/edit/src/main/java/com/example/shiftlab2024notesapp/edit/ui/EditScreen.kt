package com.example.shiftlab2024notesapp.edit.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.shiftlab2024notesapp.edit.presentation.EditState
import com.example.shiftlab2024notesapp.edit.presentation.EditViewModel
import com.example.shiftlab2024notesapp.shared.ui.ErrorComponent
import com.example.shiftlab2024notesapp.shared.ui.LoadingComponent

@Composable
fun EditScreen(
    viewModel: EditViewModel
) {

    val editState by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.showNote()
    }

    when (val state = editState) {
        is EditState.Content -> {
            ContentComponent(
                note = state.note,
                onTitleChanged = viewModel::changeTitle,
                onTextChanged = viewModel::changeText,
                onSaveClicked = viewModel::insertNote,
                onBackClicked = viewModel::closeDraft,
                onFavouriteClicked = viewModel::changeFavouriteState,
                reminderDateChanged = viewModel::createReminder,
                reminderRemoved = viewModel::removeReminder,
                requestPermissions = { context, notificationLauncher ->
                    viewModel.requestPermissions(context, notificationLauncher)
                },
                isPermissionsGranted = state.isPermissionsGranted
            )
        }

        is EditState.Failure -> {
            val context = LocalContext.current
            ErrorComponent(
                message = state.message,
                onRetry = { viewModel.insertNote(context) }
            )
        }

        EditState.Initial -> LoadingComponent()
    }
}