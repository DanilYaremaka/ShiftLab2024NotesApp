package com.example.shiftlab2024notesapp.edit.domain.usecase

import com.example.shiftlab2024notesapp.edit.domain.repository.EditRepository
import com.example.shiftlab2024notesapp.shared.entity.Note

class InsertNoteUseCase(
    private val repository: EditRepository
) {

    suspend operator fun invoke(note: Note) = repository.insertNote(note)
}