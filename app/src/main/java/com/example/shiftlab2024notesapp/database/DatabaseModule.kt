package com.example.shiftlab2024notesapp.database

import androidx.room.Room
import com.example.shiftlab2024notesapp.shared.database.NoteConverter
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

private const val DB_NAME = "notesdb"

val databaseModule = module {

    single<NotesRoomDataBase> {
        Room.databaseBuilder(
            androidContext().applicationContext,
            NotesRoomDataBase::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration().build()
    }

    factoryOf(::NoteConverter)

    single {
        val db: NotesRoomDataBase = get()
        db.noteDao()
    }
    single {
        val db: NotesRoomDataBase = get()
        db.editDao()
    }
}