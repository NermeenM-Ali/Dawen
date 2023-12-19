package com.company.dawen.model.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.company.dawen.utils.Helpers.formattedDate

@Entity(tableName = "note_table")
data class Note(
    val noteTitle: String,
    val noteDescription: String,
    val isFavourite: Boolean,
    val creationDate: String = formattedDate(),
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
