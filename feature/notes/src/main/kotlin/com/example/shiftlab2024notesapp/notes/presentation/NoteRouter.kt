package com.example.shiftlab2024notesapp.notes.presentation

import com.example.shiftlab2024notesapp.shared.entity.Note

interface NoteRouter {

    fun openNote(note: Note)

    fun openNewNote()
}