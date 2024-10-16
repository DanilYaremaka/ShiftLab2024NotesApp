package com.example.shiftlab2024notesapp.notes.data.repository

import com.example.shiftlab2024notesapp.notes.domain.entity.Note
import com.example.shiftlab2024notesapp.notes.domain.repository.NotesRepository

class NotesRepositoryImpl: NotesRepository {
    override fun getNotes(): List<Note> {
        val notesList = mutableListOf<Note>().apply {
            repeat(15) {
                add(Note(it, "title $it", "text $it"))
            }
        }
        return notesList
    }
}