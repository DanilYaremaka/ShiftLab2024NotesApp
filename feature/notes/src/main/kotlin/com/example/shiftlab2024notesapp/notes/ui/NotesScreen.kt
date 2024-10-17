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
        viewModel.loadNotes()
    }

    when (val state = notesState) {
        is NotesState.Content -> ContentComponent(
            notes = state.notes,
            onItemSelected = viewModel::openNote,
            onAddClicked = { viewModel.addNote() },
            swipedToDelete = viewModel::deleteNote,
        )

        is NotesState.Failure -> ErrorComponent(message = state.message) {
            viewModel.reloadNotes()
        }

        NotesState.Initial, NotesState.Loading -> LoadingComponent()
    }
}