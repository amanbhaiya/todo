package com.digitalamanmedia.todo.persentation.notes_edit_details.viewmodel

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteDetailEvent{
    data class EnteredTitle(val value: String): AddEditNoteDetailEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AddEditNoteDetailEvent()
    data class EnteredContent(val value: String): AddEditNoteDetailEvent()
    data class ChangeContentFocus(val focusState: FocusState): AddEditNoteDetailEvent()
    data class ChangeColor(val color: Int): AddEditNoteDetailEvent()
    object SaveNote: AddEditNoteDetailEvent()
}
