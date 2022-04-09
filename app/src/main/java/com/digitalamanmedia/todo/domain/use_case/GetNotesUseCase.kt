package com.digitalamanmedia.todo.domain.use_case

import com.digitalamanmedia.todo.domain.modal.Notes
import com.digitalamanmedia.todo.domain.repository.NotesRepository
import com.digitalamanmedia.todo.domain.utils.NoteOrder
import com.digitalamanmedia.todo.domain.utils.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotesUseCase(
    private val notesRepository: NotesRepository
) {
    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<List<Notes>> {
        return notesRepository.getNotes().map { notes ->
            when(noteOrder.orderType){
                is OrderType.Ascending -> {
                    when(noteOrder){
                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedBy { it.time }
                        is NoteOrder.Color -> notes.sortedBy { it.color }
                    }
                }
                is OrderType.Descending -> {
                    when(noteOrder){
                        is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedByDescending { it.time }
                        is NoteOrder.Color -> notes.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}