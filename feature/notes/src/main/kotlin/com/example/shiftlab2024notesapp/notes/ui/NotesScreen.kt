package com.example.shiftlab2024notesapp.notes.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.shiftlab2024notesapp.notes.presentation.NotesState
import com.example.shiftlab2024notesapp.notes.presentation.NotesViewModel
import com.example.shiftlab2024notesapp.shared.ui.ErrorComponent
import com.example.shiftlab2024notesapp.shared.ui.LoadingComponent

@Composable
fun NotesScreen(
    viewModel: NotesViewModel
) {
    val notesState by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getNotes()
    }

    when (val state = notesState) {
        is NotesState.Content -> ContentComponent(
            notes = state.notes,
            onItemSelected = viewModel::openNote,
            onAddClicked = { viewModel.openNote(null) },
            swipedToDelete = viewModel::deleteNote,
            isShowFavourite = state.isShowFavourite,
            onShowFavouriteClicked = viewModel::changeShowFavouriteState
        )

        is NotesState.Failure -> ErrorComponent(
            message = state.message,
            onRetry = { viewModel.reloadNotes() }
        )

        NotesState.Initial, NotesState.Loading -> LoadingComponent()
    }
}