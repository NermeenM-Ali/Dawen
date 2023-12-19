package com.company.dawen.utils

import com.company.dawen.model.source.local.Note


enum class NavigationType {
    ADD, EDIT
}

sealed class UiEvents {
    data object PopStack : UiEvents()
    data class SwitchMode(val isEnabled:Boolean):UiEvents()
    data class Navigate(val type: NavigationType, val noteId: Int) : UiEvents()
    data class Share(val note: Note) : UiEvents()
    data class ShowSnackBar(val message: Int) : UiEvents()
    data class DeletePopup(val note: Note, val notePosition:Int) : UiEvents()
}
