package com.example.shiftlab2024notesapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shiftlab2024notesapp.notes.data.database.NoteDao
import com.example.shiftlab2024notesapp.shared.entity.Note

@Database(
    entities = [(Note::class)],
    version = 2,
    exportSchema = false
)
abstract class NotesRoomDataBase: RoomDatabase() {

    abstract fun noteDao(): NoteDao

}