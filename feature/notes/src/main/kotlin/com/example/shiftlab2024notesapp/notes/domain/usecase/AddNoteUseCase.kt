package com.example.shiftlab2024notesapp.notes.domain.usecase

import com.example.shiftlab2024notesapp.notes.domain.repository.NotesRepository
import com.example.shiftlab2024notesapp.shared.entity.Note

class AddNoteUseCase(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(note: Note) = repository.addNote(note)
}