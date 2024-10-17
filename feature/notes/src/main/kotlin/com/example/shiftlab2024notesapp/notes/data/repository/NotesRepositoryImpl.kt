package com.example.shiftlab2024notesapp.notes.data.repository

import com.example.shiftlab2024notesapp.notes.data.database.NoteDao
import com.example.shiftlab2024notesapp.notes.domain.repository.NotesRepository
import com.example.shiftlab2024notesapp.shared.entity.Note

class NotesRepositoryImpl(
    private val noteDao: NoteDao
): NotesRepository {

    override suspend fun getNotes(): List<Note> {
        return noteDao.getNotes()
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

    override suspend fun addNote(note: Note) {
        noteDao.addNote(note)
    }
}