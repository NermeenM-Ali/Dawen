package com.company.dawen.model.repositories

import com.company.dawen.model.data.roomDB.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun insertNote(note: Note)
    suspend fun deleteNote(note: Note)
    suspend fun getNoteById(noteId: Int): Note
    fun getAllNotes(): Flow<List<Note>>
}