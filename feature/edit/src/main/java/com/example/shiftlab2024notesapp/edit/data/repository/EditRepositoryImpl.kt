package com.example.shiftlab2024notesapp.edit.data.repository

import com.example.shiftlab2024notesapp.edit.data.database.EditDao
import com.example.shiftlab2024notesapp.edit.domain.repository.EditRepository
import com.example.shiftlab2024notesapp.shared.database.NoteConverter
import com.example.shiftlab2024notesapp.shared.entity.Note

class EditRepositoryImpl(
    private val editDao: EditDao,
    private val converter: NoteConverter
): EditRepository {

    override suspend fun insertNote(note: Note) {
        editDao.insertItem(converter.convertToDTO(note))
    }
}