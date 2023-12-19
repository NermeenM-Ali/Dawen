package com.company.dawen.view.addEditNote

sealed class AddEditNoteEvents{
    data class OnTitleChange(val title:String):AddEditNoteEvents()
    data class OnDescriptionChange(val description:String):AddEditNoteEvents()
    data object OnBackClicked:AddEditNoteEvents()
    data object OnSaveClicked: AddEditNoteEvents()
}
