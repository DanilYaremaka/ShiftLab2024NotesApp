package com.example.shiftlab2024notesapp.notes.domain.usecase

import com.example.shiftlab2024notesapp.notes.domain.entity.Note
import com.example.shiftlab2024notesapp.notes.domain.repository.NotesRepository

class GetNotesUseCase(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(): List<Note> = repository.getNotes()
}