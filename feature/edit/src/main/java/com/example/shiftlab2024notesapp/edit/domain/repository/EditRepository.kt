package com.example.shiftlab2024notesapp.edit.domain.repository

import com.example.shiftlab2024notesapp.shared.entity.Note

interface EditRepository {

    suspend fun insertNote(note: Note)
}