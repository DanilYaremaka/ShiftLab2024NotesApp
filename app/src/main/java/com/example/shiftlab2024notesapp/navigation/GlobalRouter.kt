package com.example.shiftlab2024notesapp.navigation

interface GlobalRouter {

    fun openWithSavingState(route: Any)

    fun openWithRestoreState(route: Any)

    fun openWithSaveAndRestoreState(route: Any)

    fun pop()
}