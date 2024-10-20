package com.example.shiftlab2024notesapp.shared.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteDTO(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String = "",
    val text: String = "",
    val isFavourite: Boolean = false,
    val lastUpdate: Long? = null,
    val reminderDate: Long? = null
)
