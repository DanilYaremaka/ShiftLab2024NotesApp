package com.example.shiftlab2024notesapp.shared.database

import com.example.shiftlab2024notesapp.shared.entity.Note

class NoteConverter {

    fun convertToEntity(noteDTO: NoteDTO) = Note(
        id = noteDTO.id,
        title = noteDTO.title,
        text = noteDTO.text,
        isFavourite = noteDTO.isFavourite,
        lastUpdate = noteDTO.lastUpdate
    )

    fun convertToDTO(note: Note) = NoteDTO(
        id = note.id,
        title = note.title,
        text = note.text,
        isFavourite = note.isFavourite,
        lastUpdate = note.lastUpdate
    )
}