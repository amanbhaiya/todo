package com.digitalamanmedia.todo.data.data_source


import androidx.room.*
import com.digitalamanmedia.todo.domain.modal.Notes
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDAO {
    @Query("SELECT * FROM notes")
    fun getNotes() : Flow<List<Notes>>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNoteById(id : Int):Notes?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(notes: Notes)

    @Delete
    suspend fun deleteNotes(notes: Notes)
}