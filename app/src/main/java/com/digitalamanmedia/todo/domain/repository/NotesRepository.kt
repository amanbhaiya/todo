package com.digitalamanmedia.todo.domain.repository

import com.digitalamanmedia.todo.domain.modal.Notes
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

     fun getNotes(): Flow<List<Notes>>

    suspend fun getNoteById(id : Int):Notes?

    suspend fun insertNote(notes: Notes)

    suspend fun deleteNotes(notes: Notes)

}