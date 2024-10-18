package com.example.shiftlab2024notesapp.notes.data.repository

import com.example.shiftlab2024notesapp.notes.data.database.NoteDao
import com.example.shiftlab2024notesapp.notes.domain.repository.NotesRepository
import com.example.shiftlab2024notesapp.shared.database.NoteConverter
import com.example.shiftlab2024notesapp.shared.entity.Note

class NotesRepositoryImpl(
    private val noteDao: NoteDao,
    private val converter: NoteConverter
): NotesRepository {

    override suspend fun getNotes(): List<Note> {
        return noteDao.getNotes().map {
            converter.convertToEntity(it)
        }
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(converter.convertToDTO(note))
    }

}