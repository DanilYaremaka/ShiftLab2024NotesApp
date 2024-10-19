package com.example.shiftlab2024notesapp.edit.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.shiftlab2024notesapp.shared.database.NoteDTO

@Dao
interface EditDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(note: NoteDTO)
}