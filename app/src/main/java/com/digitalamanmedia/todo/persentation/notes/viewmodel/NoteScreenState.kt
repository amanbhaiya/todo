package com.digitalamanmedia.todo.persentation.notes.viewmodel

import com.digitalamanmedia.todo.domain.modal.Notes
import com.digitalamanmedia.todo.domain.utils.NoteOrder
import com.digitalamanmedia.todo.domain.utils.OrderType

data class NoteScreenState(
    val isToggleButtonsVisible: Boolean = false,
    val notes:List<Notes> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
)