package com.digitalamanmedia.todo.persentation.notes.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalamanmedia.todo.domain.modal.Notes
import com.digitalamanmedia.todo.domain.use_case.AllUseCases
import com.digitalamanmedia.todo.domain.utils.NoteOrder
import com.digitalamanmedia.todo.domain.utils.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val allUseCases: AllUseCases
): ViewModel(){


    private val _state = mutableStateOf(NoteScreenState())
    val state: State<NoteScreenState> = _state

    private var recentlyDeletedNote: Notes? = null
    private var job: Job? = null
    init {
        getNotes(noteOrder = NoteOrder.Date(OrderType.Descending))
    }


    fun onEvent(event: NoteUiEvent){
        when(event){
            is NoteUiEvent.Order->{
                if (state.value.noteOrder::class == event.noteOrder::class &&
                        state.value.noteOrder.orderType == event.noteOrder.orderType){
                    return
                }
                getNotes(event.noteOrder)
            }
            is NoteUiEvent.DeleteNote->{
                viewModelScope.launch {
                    allUseCases.deleteNoteUseCase(event.notes)
                    recentlyDeletedNote = event.notes
                }

            }
            is NoteUiEvent.RestoreNote->{
                viewModelScope.launch {
                    allUseCases.insertNoteUseCase(recentlyDeletedNote?:return@launch)
                    recentlyDeletedNote = null
                }

            }
            is NoteUiEvent.ToggleOrderSection->{
                _state.value = state.value.copy(
                    isToggleButtonsVisible = !state.value.isToggleButtonsVisible
                )

            }
        }
    }
    private fun getNotes(noteOrder: NoteOrder){
       job = allUseCases.getNotesUseCase(noteOrder)
            .onEach {
                _state.value = state.value.copy(
                    notes = it,
                    noteOrder = noteOrder
                )
            }.launchIn(viewModelScope)
    }
}