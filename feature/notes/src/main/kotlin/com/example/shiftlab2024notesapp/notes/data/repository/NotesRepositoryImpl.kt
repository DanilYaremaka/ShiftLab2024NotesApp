package com.example.shiftlab2024notesapp.notes.data.repository

import com.example.shiftlab2024notesapp.notes.domain.entity.Note
import com.example.shiftlab2024notesapp.notes.domain.repository.NotesRepository

class NotesRepositoryImpl: NotesRepository {
    override fun getNotes(): List<Note> {
        val notesList = mutableListOf<Note>().apply {
            repeat(5) {
                add(Note(it, "title $it title $it title $it title $it title $it", "text $it\n text $it\n text $it\n text $it\ntext $it\n"))
            }
            repeat(6) {
                add(Note(it, "title $it", "text $it\n text $it\n"))
            }
            repeat(5) {
                add(Note(it, "title $it", "text $it\ntext $it\ntext $it\ntext $it\n"))
            }
        }
        return notesList
    }
}