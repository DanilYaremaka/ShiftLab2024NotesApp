package com.example.shiftlab2024notesapp.shared.entity

import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id: Int? = null,
    val title: String = "",
    val text: String = ""
)
