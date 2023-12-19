package com.company.dawen.view.notesList

import com.company.dawen.model.data.roomDB.Note

sealed class NoteListEvents {
    data object OnAddNoteClicked : NoteListEvents()
    data class OnSwitchUiModeClicked(val isEnabled: Boolean) : NoteListEvents()
    data class OnNoteClicked(val noteId: Int) : NoteListEvents()
    data class OnShareClicked(val note: Note) : NoteListEvents()
    data class OnDeleteNoteClicked(val note: Note, val notePosition: Int) : NoteListEvents()
    data class OnAddToFavouriteClicked(val note: Note, val isFavourite: Boolean) : NoteListEvents()
}
