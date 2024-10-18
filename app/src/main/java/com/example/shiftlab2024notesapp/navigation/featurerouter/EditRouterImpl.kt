package com.example.shiftlab2024notesapp.navigation.featurerouter

import NotesRoute
import com.example.shiftlab2024notesapp.edit.presentation.EditRouter
import com.example.shiftlab2024notesapp.navigation.GlobalRouter

class EditRouterImpl(
    private val router: GlobalRouter
): EditRouter {

    override fun close() {
        router.openWithSaveAndRestoreState(NotesRoute)
    }

    override fun saveAndClose() {
        router.openWithRestoreState(NotesRoute)
    }
}