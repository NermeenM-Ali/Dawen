package com.company.dawen.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.dawen.model.repositories.NoteRepository
import com.company.dawen.model.source.local.Note
import com.company.dawen.utils.NavigationType
import com.company.dawen.utils.UiEvents
import com.company.dawen.view.notesList.NoteListEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {
    private val tag = "NotesListViewModel"
    private var _notesList: Flow<List<Note>> = emptyFlow()


    val notesList: Flow<List<Note>>
        get() = _notesList

    private val _loadingState = MutableStateFlow(true)
    val loadingState: StateFlow<Boolean>
        get() = _loadingState

    private val _uiEvent = Channel<UiEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()// 3l4an n3ml receive ll event just once mara wa7da

    init {
        fetchNotesData()
    }

    private fun fetchNotesData() {
        viewModelScope.launch {
            _loadingState.value = true
            try {
                _notesList = repository.getAllNotes()
                _loadingState.value = false
            } catch (e: Exception) {
                // Handle errors
                _notesList = emptyFlow()
                _loadingState.value = false
                e.localizedMessage?.let { Log.d(tag, it) }
            }
        }
    }

    fun onEvent(event: NoteListEvents) {
        when (event) {

            is NoteListEvents.OnNoteClicked -> {
                sendUiEvent(UiEvents.Navigate(NavigationType.EDIT, event.noteId))
            }

            is NoteListEvents.OnAddNoteClicked -> {
                sendUiEvent(UiEvents.Navigate(NavigationType.ADD, -1))
            }

            is NoteListEvents.OnShareClicked -> {
                sendUiEvent(
                    UiEvents.Share(event.note)
                )
            }

            is NoteListEvents.OnSwitchUiModeClicked -> {
                sendUiEvent(
                    UiEvents.SwitchMode(event.isEnabled),
                )
            }

            is NoteListEvents.OnDeleteNoteClicked -> {
                sendUiEvent(UiEvents.DeletePopup(event.note, event.notePosition))
            }

            is NoteListEvents.OnAddToFavouriteClicked -> {
                viewModelScope.launch {
                    repository.insertNote(event.note.copy(isFavourite = event.isFavourite))
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvents) {
        viewModelScope.launch { _uiEvent.send(event) }
    }

    fun handleDeletingNote(note: Note){
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

}