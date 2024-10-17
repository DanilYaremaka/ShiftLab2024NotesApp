package com.example.shiftlab2024notesapp.edit.ui

import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable

@Composable
fun EditScreen(
    id: Int
) {

    TextField(value = id.toString(), onValueChange = {})

}