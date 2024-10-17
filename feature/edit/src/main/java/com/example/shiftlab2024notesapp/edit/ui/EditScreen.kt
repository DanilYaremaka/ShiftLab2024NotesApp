package com.example.shiftlab2024notesapp.edit.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import com.example.shiftlab2024notesapp.shared.entity.Note

@Composable
fun EditScreen(
    note: Note
) {
    Column {
        TextField(value = note.title, onValueChange = {})
        TextField(value = note.text, onValueChange = {})
        TextField(value = note.id.toString(), onValueChange = {})
    }
}