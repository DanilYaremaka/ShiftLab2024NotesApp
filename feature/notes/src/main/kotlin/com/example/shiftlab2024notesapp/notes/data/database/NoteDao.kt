package com.example.shiftlab2024notesapp.notes.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.example.shiftlab2024notesapp.shared.database.NoteDTO

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY lastUpdate desc")
    suspend fun getNotes(): List<NoteDTO>

    @Delete
    suspend fun deleteNote(note: NoteDTO)

}