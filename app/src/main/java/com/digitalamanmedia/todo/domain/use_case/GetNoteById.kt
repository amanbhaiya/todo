package com.digitalamanmedia.todo.domain.use_case

import com.digitalamanmedia.todo.domain.modal.Notes
import com.digitalamanmedia.todo.domain.repository.NotesRepository

class GetNoteById(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(id: Int): Notes?{
        return repository.getNoteById(id)
    }
}