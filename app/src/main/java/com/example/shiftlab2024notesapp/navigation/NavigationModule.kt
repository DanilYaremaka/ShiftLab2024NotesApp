package com.example.shiftlab2024notesapp.navigation

import com.example.shiftlab2024notesapp.edit.presentation.EditRouter
import com.example.shiftlab2024notesapp.navigation.featurerouter.EditRouterImpl
import com.example.shiftlab2024notesapp.navigation.featurerouter.NoteRouterImpl
import com.example.shiftlab2024notesapp.notes.presentation.NoteRouter
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.binds
import org.koin.dsl.module

val navModule = module {

    singleOf(::GlobalRouterImpl) binds arrayOf(GlobalRouter::class, NavControllerHolder::class)

    singleOf(::NoteRouterImpl) bind NoteRouter::class
    singleOf(::EditRouterImpl) bind EditRouter::class
}