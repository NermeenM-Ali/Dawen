package com.company.dawen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.dawen.R
import com.company.dawen.model.repositories.NoteRepository
import com.company.dawen.model.data.roomDB.Note
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
    private lateinit var noteToEdit: Note


    var title = MutableStateFlow("")
        private set

    var description = MutableStateFlow("")
        private set


    private val _uiEvent = Channel<UiEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()// 3l4an n3ml receive ll event just once mara wa7da

    fun fetchNoteById(noteId: Int) {
        this.noteId = noteId
        viewModelScope.launch {
            if (noteId != -1) {
                val note = repository.getNoteById(noteId)
                title.value = note.noteTitle
                description.value = note.noteDescription
                noteToEdit = note
            }
        }
    }

    fun onEvent(event: AddEditNoteEvents) {
        when (event) {

            is AddEditNoteEvents.OnTitleChange -> {
                title.value = event.title
            }

            is AddEditNoteEvents.OnDescriptionChange -> {
                description.value = event.description
            }

            is AddEditNoteEvents.OnBackClicked -> {
                sendUiEvent(UiEvents.PopStack)
            }

            is AddEditNoteEvents.OnSaveClicked -> {
                if (noteId == -1) {
                    addNote()
                } else {
                    editNote()
                }

            }


        }
    }


    private fun sendUiEvent(event: UiEvents) {
        viewModelScope.launch { _uiEvent.send((event)) }
    }

    private fun addNote() {
        if (title.value.isEmpty()) {
            sendUiEvent(UiEvents.ShowSnackBar(R.string.title_required))
        } else {
            viewModelScope.launch {
                repository.insertNote(
                    Note(
                        noteTitle = title.value,
                        noteDescription = description.value,
                        isFavourite = false
                    )
                )
            }
            sendUiEvent(UiEvents.PopStack)

        }
    }

    private fun editNote() {
        if (title.value.isEmpty()) {
            sendUiEvent(UiEvents.ShowSnackBar(R.string.title_required))
        } else {
            viewModelScope.launch {
                repository.insertNote(
                    Note(
                        noteTitle = title.value,
                        noteDescription = description.value,
                        id = noteToEdit.id,
                        isFavourite = noteToEdit.isFavourite,
                        creationDate = noteToEdit.creationDate
                    )
                )
            }
            sendUiEvent(UiEvents.PopStack)
        }
    }
}