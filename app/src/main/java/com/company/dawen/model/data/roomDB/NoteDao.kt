package com.company.dawen.model.data.roomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Upsert
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("Select * from note_table where id= :noteId")
    suspend fun getNoteById(noteId: Int): Note

    @Query("Select * from note_table order by creationDate ASC")
    fun getAllNotes():Flow<List<Note>>

}