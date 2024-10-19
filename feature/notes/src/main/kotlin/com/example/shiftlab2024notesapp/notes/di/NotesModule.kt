package com.example.shiftlab2024notesapp.notes.di

import com.example.shiftlab2024notesapp.notes.data.repository.NotesRepositoryImpl
import com.example.shiftlab2024notesapp.notes.domain.repository.NotesRepository
import com.example.shiftlab2024notesapp.notes.domain.usecase.DeleteNoteUseCase
import com.example.shiftlab2024notesapp.notes.domain.usecase.GetNotesUseCase
import com.example.shiftlab2024notesapp.notes.presentation.NotesViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val notesModule = module {

    singleOf(::NotesRepositoryImpl) bind NotesRepository::class

    factoryOf(::GetNotesUseCase)
    factoryOf(::DeleteNoteUseCase)

    viewModelOf(::NotesViewModel)
}