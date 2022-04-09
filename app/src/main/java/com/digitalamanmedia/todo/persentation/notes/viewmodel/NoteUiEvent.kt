package com.digitalamanmedia.todo.persentation.notes.viewmodel

import com.digitalamanmedia.todo.domain.modal.Notes
import com.digitalamanmedia.todo.domain.utils.NoteOrder

sealed class NoteUiEvent{
    data class Order(val noteOrder: NoteOrder): NoteUiEvent()
    data class DeleteNote(val notes: Notes): NoteUiEvent()
    object RestoreNote: NoteUiEvent()
    object ToggleOrderSection: NoteUiEvent()
}