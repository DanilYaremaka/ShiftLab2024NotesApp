package com.example.shiftlab2024notesapp.notes.presentation

import com.example.shiftlab2024notesapp.notes.domain.entity.Note

sealed interface NotesState {

    data object Initial: NotesState
    data object Loading: NotesState
    data class Content(val notes: List<Note>): NotesState
    data class Failure(val message: String): NotesState
}