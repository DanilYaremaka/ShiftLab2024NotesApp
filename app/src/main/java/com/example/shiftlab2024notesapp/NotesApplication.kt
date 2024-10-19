package com.example.shiftlab2024notesapp

import android.app.Application
import com.example.shiftlab2024notesapp.database.databaseModule
import com.example.shiftlab2024notesapp.edit.di.editModule
import com.example.shiftlab2024notesapp.navigation.navModule
import com.example.shiftlab2024notesapp.notes.di.notesModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NotesApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@NotesApplication)
            modules(
                notesModule,
                navModule,
                databaseModule,
                editModule
            )
        }
    }
}