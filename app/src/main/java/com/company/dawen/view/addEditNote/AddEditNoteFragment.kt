package com.company.dawen.view.addEditNote

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.company.dawen.R
import com.company.dawen.databinding.FragmentAddEditNoteBinding
import com.company.dawen.utils.UiEvents
import com.company.dawen.viewModel.AddEditNoteViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddEditNoteFragment : Fragment(R.layout.fragment_add_edit_note) {
    private lateinit var binding: FragmentAddEditNoteBinding
    private val addEditNoteViewModel by viewModels<AddEditNoteViewModel>()
    private val args: AddEditNoteFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddEditNoteBinding.bind(view)

        // Get the noteId from arguments
        val noteId = args.noteId
        addEditNoteViewModel.fetchNoteById(noteId)

        binding.apply {
            backBtn.setOnClickListener { addEditNoteViewModel.onEvent(AddEditNoteEvents.OnBackClicked) }
            saveBtn.setOnClickListener { addEditNoteViewModel.onEvent(AddEditNoteEvents.OnSaveClicked(addEditNoteTitle.text.toString(), addEditNoteDescription.text.toString())) }
        }

        fillInputsWithNoteData()
        handleUiEvents()
    }

    private fun fillInputsWithNoteData() {
        lifecycleScope.launch {
            addEditNoteViewModel.noteToEdit.collect { note -> binding.addEditNoteTitle.setText(note.noteTitle) }

        }
        lifecycleScope.launch {
            addEditNoteViewModel.noteToEdit.collect { note ->
                binding.addEditNoteDescription.setText(
                    note.noteDescription
                )
            }
        }
    }


    private fun handleUiEvents() {
        lifecycleScope.launch {
            addEditNoteViewModel.uiEvent.collect { event ->
                when (event) {
                    is UiEvents.PopStack -> navigateBack()
                    is UiEvents.ShowSnackBar -> showSnackBar(event.message)
                    else -> Unit
                }
            }
        }
    }

    private fun showSnackBar(message: Int) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun navigateBack() {
        val navController = NavHostFragment.findNavController(this)
        navController.popBackStack()
    }
}