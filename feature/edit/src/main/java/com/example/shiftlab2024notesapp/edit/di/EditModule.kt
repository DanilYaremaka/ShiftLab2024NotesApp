package com.example.shiftlab2024notesapp.edit.di

import com.example.shiftlab2024notesapp.edit.data.repository.EditRepositoryImpl
import com.example.shiftlab2024notesapp.edit.domain.repository.EditRepository
import com.example.shiftlab2024notesapp.edit.domain.usecase.InsertNoteUseCase
import com.example.shiftlab2024notesapp.edit.presentation.EditViewModel
import com.example.shiftlab2024notesapp.shared.entity.Note
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val editModule = module {

    singleOf(::EditRepositoryImpl) bind EditRepository::class

    factoryOf(::InsertNoteUseCase)

    viewModel {(note: Note) ->
        EditViewModel(
            note = note,
            insertNoteUseCase = get(),
            router = get()
        )
    }
}