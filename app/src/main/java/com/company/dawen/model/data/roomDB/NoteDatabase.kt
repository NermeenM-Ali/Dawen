package com.company.dawen.model.data.roomDB

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase:RoomDatabase() {
    abstract val dao: NoteDao
}