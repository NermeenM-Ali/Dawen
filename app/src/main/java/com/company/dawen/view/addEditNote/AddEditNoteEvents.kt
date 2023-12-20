package com.company.dawen.view.addEditNote

sealed class AddEditNoteEvents{
    data object OnBackClicked:AddEditNoteEvents()
    data class OnSaveClicked(val noteTitle:String, val noteDescription:String): AddEditNoteEvents()
}
