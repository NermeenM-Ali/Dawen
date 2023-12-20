package com.company.dawen.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.dawen.R
import com.company.dawen.model.repositories.NoteRepository
import com.company.dawen.model.source.local.Note
import com.company.dawen.utils.UiEvents
import com.company.dawen.view.addEditNote.AddEditNoteEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val repository: NoteRepository,
) : ViewModel() {
    private var noteId: Int = -1
    var noteToEdit: MutableStateFlow<Note> =
        MutableStateFlow(Note(noteTitle = "", noteDescription = "", isFavourite = false))
        private set


    private val _uiEvent = Channel<UiEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()// 3l4an n3ml receive ll event just once mara wa7da

    fun fetchNoteById(noteId: Int) {
        this.noteId = noteId
        viewModelScope.launch {
            if (noteId != -1) {
                val note = repository.getNoteById(noteId)
                noteToEdit.value = note
            }
        }
    }

    fun onEvent(event: AddEditNoteEvents) {
        when (event) {

            is AddEditNoteEvents.OnBackClicked -> {
                sendUiEvent(UiEvents.PopStack)
            }

            is AddEditNoteEvents.OnSaveClicked -> {
                if (noteId == -1) {
                    addNote(event.noteTitle, event.noteDescription)
                } else {
                    editNote(event.noteTitle, event.noteDescription)
                }

            }

        }
    }


    private fun sendUiEvent(event: UiEvents) {
        viewModelScope.launch { _uiEvent.send((event)) }
    }

    private fun addNote(noteTitle: String, noteDescription: String) {
        if (noteTitle.isEmpty()) {
            sendUiEvent(UiEvents.ShowSnackBar(R.string.title_required))
        } else {
            viewModelScope.launch {
                repository.insertNote(
                    Note(
                        noteTitle = noteTitle,
                        noteDescription = noteDescription,
                        isFavourite = false
                    )
                )
            }
            sendUiEvent(UiEvents.PopStack)

        }
    }

    private fun editNote(noteTitle: String, noteDescription: String) {
        if (noteTitle.isEmpty()) {
            sendUiEvent(UiEvents.ShowSnackBar(R.string.title_required))
        } else {
            viewModelScope.launch {
                repository.insertNote(
                    Note(
                        noteTitle = noteTitle,
                        noteDescription = noteDescription,
                        id = noteToEdit.value.id,
                        isFavourite = noteToEdit.value.isFavourite,
                        creationDate = noteToEdit.value.creationDate
                    )
                )
            }
            sendUiEvent(UiEvents.PopStack)
        }
    }
}