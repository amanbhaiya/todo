package com.digitalamanmedia.todo.domain.use_case

import com.digitalamanmedia.todo.domain.modal.Notes
import com.digitalamanmedia.todo.domain.repository.NotesRepository

class DeleteNoteUseCase(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(notes: Notes){
         repository.deleteNotes(notes)
    }
}