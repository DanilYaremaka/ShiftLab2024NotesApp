package com.example.shiftlab2024notesapp

import android.app.Application
import com.example.shiftlab2024notesapp.di.mainModule
import com.example.shiftlab2024notesapp.notes.di.notesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class NotesApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(this@NotesApplication)
            modules(
                mainModule,
                notesModule
            )
        }
    }
}