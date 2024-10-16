package com.example.shiftlab2024notesapp.notes.domain.repository

import com.example.shiftlab2024notesapp.notes.domain.entity.Note

interface NotesRepository {

    fun getNotes(): List<Note>
}