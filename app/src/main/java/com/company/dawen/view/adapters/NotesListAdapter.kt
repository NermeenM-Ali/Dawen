package com.company.dawen.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.company.dawen.R
import com.company.dawen.model.source.local.Note
import com.company.dawen.view.notesList.NoteListEvents
import com.company.dawen.viewModel.NotesListViewModel

class NotesListAdapter(
    private var notes: List<Note>,
    private val notesListViewModel: NotesListViewModel
) : RecyclerView.Adapter<NotesListAdapter.NotesListViewHolder>() {

    class NotesListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteCard: CardView = itemView.findViewById(R.id.note_card)
        val noteTitle: TextView = itemView.findViewById(R.id.note_title)
        val noteDescription: TextView = itemView.findViewById(R.id.note_description)
        val noteCreationDate: TextView = itemView.findViewById(R.id.note_creation_date)
        val deleteNote: ImageView = itemView.findViewById(R.id.delete_note)
        val shareNote: ImageView = itemView.findViewById(R.id.share_note)
        val addNoteToFavourites: ToggleButton = itemView.findViewById(R.id.note_favourite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NotesListViewHolder(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NotesListViewHolder, position: Int) {
        val note: Note = notes[position]
        holder.apply {
            noteTitle.text = note.noteTitle
            noteDescription.text = note.noteDescription
            noteCreationDate.text = note.creationDate
            addNoteToFavourites.isChecked = note.isFavourite
            if (note.isFavourite) {
                addNoteToFavourites.setButtonDrawable(R.drawable.favourite)
            } else {
                addNoteToFavourites.setButtonDrawable(R.drawable.not_favourite)
            }
            noteCard.setOnClickListener {
                notesListViewModel.onEvent(NoteListEvents.OnNoteClicked(note.id))
            }

            shareNote.setOnClickListener {
                notesListViewModel.onEvent(
                    NoteListEvents.OnShareClicked(note)
                )
            }

            deleteNote.setOnClickListener {
                notesListViewModel.onEvent(
                    NoteListEvents.OnDeleteNoteClicked(
                        note,
                        position
                    )
                )
            }


            addNoteToFavourites.setOnCheckedChangeListener { _, isChecked ->
                addNoteToFavourites.isChecked = isChecked
                notesListViewModel.onEvent(
                    NoteListEvents.OnAddToFavouriteClicked(
                        note,
                        isChecked,
                    )
                )

            }
        }

    }

//    companion object {
//        private val DiffCallback = object : DiffUtil.ItemCallback<Note>() {
//            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
//                return oldItem.id == newItem.id
//            }
//
//            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
//                return oldItem == newItem
//            }
//        }
//    }
}

