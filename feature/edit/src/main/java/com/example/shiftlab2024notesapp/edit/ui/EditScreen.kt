package com.example.shiftlab2024notesapp.edit.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.shiftlab2024notesapp.shared.entity.Note

@Composable
fun EditScreen(
    note: Note
) {
    
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            TextField(value = note.title, onValueChange = {})
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = note.text, onValueChange = {})
        }
    }
}