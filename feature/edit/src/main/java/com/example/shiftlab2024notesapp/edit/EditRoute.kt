package com.example.shiftlab2024notesapp.edit

import com.example.shiftlab2024notesapp.shared.entity.Note
import kotlinx.serialization.Serializable

@Serializable
data class EditRoute(val note: Note)