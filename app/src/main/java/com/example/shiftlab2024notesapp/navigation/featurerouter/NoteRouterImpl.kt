package com.example.shiftlab2024notesapp.navigation.featurerouter

import com.example.shiftlab2024notesapp.edit.EditRoute
import com.example.shiftlab2024notesapp.navigation.GlobalRouter
import com.example.shiftlab2024notesapp.notes.presentation.NoteRouter

class NoteRouterImpl(
    private val router: GlobalRouter
): NoteRouter {

    override fun openEdit(noteId: Int) {
        router.open(EditRoute(noteId))
    }
}