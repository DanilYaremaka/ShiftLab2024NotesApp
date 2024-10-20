package com.example.shiftlab2024notesapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shiftlab2024notesapp.edit.data.database.EditDao
import com.example.shiftlab2024notesapp.notes.data.database.NoteDao
import com.example.shiftlab2024notesapp.shared.database.NoteDTO

@Database(
    entities = [(NoteDTO::class)],
    version = 6,
    exportSchema = false
)
abstract class NotesRoomDataBase: RoomDatabase() {

    abstract fun noteDao(): NoteDao

    abstract fun editDao(): EditDao

}