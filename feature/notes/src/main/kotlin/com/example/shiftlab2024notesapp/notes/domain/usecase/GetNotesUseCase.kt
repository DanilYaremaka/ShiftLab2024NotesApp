package com.example.shiftlab2024notesapp.notes.domain.usecase

import com.example.shiftlab2024notesapp.notes.domain.repository.NotesRepository
import com.example.shiftlab2024notesapp.shared.entity.Note

class GetNotesUseCase(
    private val repository: NotesRepository
) {
    suspend operator fun invoke():List<Note> = repository.getNotes()
}