package com.example.shiftlab2024notesapp.edit.presentation

import com.example.shiftlab2024notesapp.shared.entity.Note

sealed interface EditState {

    data object Initial: EditState
    data class Content(val note: Note): EditState
    data class Failure(val message: String): EditState
}