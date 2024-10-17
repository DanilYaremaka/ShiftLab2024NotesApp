package com.example.shiftlab2024notesapp.notes.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shiftlab2024notesapp.shared.entity.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes")
    suspend fun getNotes(): List<Note>

    @Delete
    suspend fun deleteNote(noteEntity: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note)
}