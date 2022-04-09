package com.digitalamanmedia.todo.domain.use_case

import com.digitalamanmedia.todo.domain.modal.InvalidNoteException
import com.digitalamanmedia.todo.domain.modal.Notes
import com.digitalamanmedia.todo.domain.repository.NotesRepository

class InsertNoteUseCase(
    private val repository: NotesRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(notes: Notes){
        if (notes.title.isBlank()){
            throw InvalidNoteException("The title of Note can't be empty...")
        }
        if (notes.content.isBlank()){
            throw InvalidNoteException("The content of Note can't be empty...")

        }
        repository.insertNote(notes = notes)
    }
}