package com.example.shiftlab2024notesapp.navigation.featurerouter

import com.example.shiftlab2024notesapp.edit.EditRoute
import com.example.shiftlab2024notesapp.navigation.GlobalRouter
import com.example.shiftlab2024notesapp.notes.presentation.NoteRouter
import com.example.shiftlab2024notesapp.shared.entity.Note

class NoteRouterImpl(
    private val router: GlobalRouter
): NoteRouter {

    override fun openEdit(note: Note) {
        router.open(EditRoute(note))
    }
}