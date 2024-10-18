package com.example.shiftlab2024notesapp.notes.domain.repository

import com.example.shiftlab2024notesapp.shared.entity.Note

interface NotesRepository {

    suspend fun getNotes(): List<Note>

    suspend fun deleteNote(note: Note)

}