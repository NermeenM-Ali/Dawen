package com.company.dawen.view.notesList

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.dawen.R
import com.company.dawen.model.source.local.Note
import com.company.dawen.databinding.FragmentNotesListBinding
import com.company.dawen.utils.NavigationType
import com.company.dawen.utils.UiEvents
import com.company.dawen.view.adapters.NotesListAdapter
import com.company.dawen.viewModel.NotesListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotesListFragment : Fragment(R.layout.fragment_notes_list) {

    private lateinit var binding: FragmentNotesListBinding
    private val notesViewModel by viewModels<NotesListViewModel>()
    private lateinit var notesListAdapter: NotesListAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNotesListBinding.bind(view)



        binding.apply {
            addNote.setOnClickListener {
                notesViewModel.onEvent(NoteListEvents.OnNoteClicked(-1))
            }

        }
        handleRecyclerView()
        handleUiEvents()

    }



    private fun handleRecyclerView() {
        lifecycleScope.launch {
            notesViewModel.loadingState.collect { isLoading ->
                if (isLoading) {
                    showLoadingView()
                } else {
                    notesViewModel.notesList
                        .collect { notes ->
                            notesListAdapter = NotesListAdapter(notes, notesViewModel)
                            binding.recyclerView.apply {
                                layoutManager = LinearLayoutManager(context)
                                adapter = notesListAdapter
                            }
                            handleViews(notes.isNotEmpty())
                        }
                }


            }
        }
    }


    private fun handleViews(isNotEmpty: Boolean) {
        if (isNotEmpty) {
            showRecyclerView()
        } else {
            showEmptyView()
        }

    }

    private fun showLoadingView() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
            emptyListLayout.visibility = View.GONE
        }
    }

    private fun showEmptyView() {
        binding.apply {
            recyclerView.visibility = View.GONE
            emptyListLayout.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }

    private fun showRecyclerView() {
        binding.apply {
            recyclerView.visibility = View.VISIBLE
            emptyListLayout.visibility = View.GONE
            progressBar.visibility = View.GONE
        }
    }


    private fun handleUiEvents() {
        lifecycleScope.launch {
            notesViewModel.uiEvent.collect { event ->
                when (event) {
                    is UiEvents.Navigate -> {
                        navigateToAddEditNote(event.type, event.noteId)
                    }

                    is UiEvents.Share -> {
                        shareNote(event.note)
                    }

                    is UiEvents.DeletePopup -> {
                        showDeletePopup(event.note, event.notePosition)
                    }

                    else -> Unit
                }
            }
        }
    }


    private fun showDeletePopup(note: Note, notePosition: Int) {
        AlertDialog.Builder(requireContext(), R.style.CustomAlertDialogStyle)
            .setTitle(R.string.delete_note)
            .setMessage(getString(R.string.delete_note_message, note.noteTitle))
            .setIcon(R.drawable.trash)
            .setPositiveButton(R.string.delete) { _, _ ->
                deleteNote(note, notePosition)
            }
            .setNegativeButton(R.string.cancel) { dialogInterface, _ -> dialogInterface.cancel() }
            .create().show()
    }

    private fun deleteNote(note: Note, notePosition: Int) {
        lifecycleScope.launch {
            notesViewModel.handleDeletingNote(note)
            notesListAdapter.notifyItemRemoved(notePosition)
        }
    }

    private fun shareNote(note: Note) {
        Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(
                Intent.EXTRA_TEXT,
                resources.getString(R.string.share_message, note.noteTitle, note.noteDescription)
            )
            startActivity(this)
        }
    }


    private fun navigateToAddEditNote(type: NavigationType, noteId: Int) {
        val navController = NavHostFragment.findNavController(this)
        val action =
            NotesListFragmentDirections.actionNotesListFragmentToAddEditNoteFragment(
                type,
                noteId
            )
        navController.navigate(action)
    }
}


