package com.digitalamanmedia.todo.persentation.notes_edit_details.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalamanmedia.todo.domain.modal.InvalidNoteException
import com.digitalamanmedia.todo.domain.modal.Notes
import com.digitalamanmedia.todo.domain.use_case.AllUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val allUseCases: AllUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel(){

    private val _noteTitle = mutableStateOf(NoteAddEditState(
        hint = "Enter Title..."
    ))
    val noteTitle: State<NoteAddEditState> = _noteTitle

    private val _noteContent = mutableStateOf(NoteAddEditState(
        hint = "Enter Some Content..."
    ))
    val noteContent: State<NoteAddEditState> = _noteContent


    private val _noteColor = mutableStateOf(Notes.noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor



    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null
    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1){
                viewModelScope.launch {
                    allUseCases.getNoteById(noteId)?.also { note->
                        currentNoteId = note.id
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteContent.value = noteContent.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        _noteColor.value = note.color
                    }
                }
            }
        }
    }


    fun onAddEvent(event: AddEditNoteDetailEvent){
        when(event){
            is AddEditNoteDetailEvent.EnteredTitle->{
                _noteTitle.value = noteTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteDetailEvent.ChangeTitleFocus->{
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _noteTitle.value.text.isBlank()
                )
            }
            is AddEditNoteDetailEvent.EnteredContent->{
                _noteContent.value = noteContent.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteDetailEvent.ChangeContentFocus->{
                _noteContent.value = noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _noteContent.value.text.isBlank()
                )
            }
            is AddEditNoteDetailEvent.ChangeColor->{
                _noteColor.value = event.color
            }

            is AddEditNoteDetailEvent.SaveNote->{
                viewModelScope.launch{
                    try {
                        allUseCases.insertNoteUseCase(
                            Notes(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                color = noteColor.value,
                                time = System.currentTimeMillis(),
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    }catch (e: InvalidNoteException){
                        _eventFlow.emit(UiEvent.ShowSnackBar("Couldn't Save Note.."))
                    }

                }
            }
        }
    }


    sealed class UiEvent{
        data class ShowSnackBar(val message: String): UiEvent()
        object SaveNote: UiEvent()
    }
}
