package com.digitalamanmedia.todo.data.repository

import com.digitalamanmedia.todo.data.data_source.NoteDAO
import com.digitalamanmedia.todo.domain.modal.Notes
import com.digitalamanmedia.todo.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class NoteRepositoryImpl @Inject constructor(
   private val noteDAO: NoteDAO
): NotesRepository{
    override fun getNotes(): Flow<List<Notes>> {
       return noteDAO.getNotes()
    }

    override suspend fun getNoteById(id: Int): Notes? {
        return noteDAO.getNoteById(id = id)
    }

    override suspend fun insertNote(notes: Notes) {
        noteDAO.insertNote(notes = notes)
    }

    override suspend fun deleteNotes(notes: Notes) {
        noteDAO.deleteNotes(notes = notes)
    }
}