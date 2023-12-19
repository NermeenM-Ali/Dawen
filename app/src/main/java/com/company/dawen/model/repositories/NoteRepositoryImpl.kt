package com.company.dawen.model.repositories

import com.company.dawen.model.source.local.Note
import com.company.dawen.model.source.local.NoteDao
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {

    override suspend fun insertNote(note: Note) = dao.insertNote(note)

    override suspend fun deleteNote(note: Note) = dao.deleteNote(note)

    override suspend fun getNoteById(noteId: Int): Note = dao.getNoteById(noteId)

    override fun getAllNotes(): Flow<List<Note>> = dao.getAllNotes()
}