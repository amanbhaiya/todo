package com.digitalamanmedia.todo.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase

import com.digitalamanmedia.todo.domain.modal.Notes

@Database(
    entities = [Notes::class],
    version = 1
)
abstract class NoteDataBase: RoomDatabase() {

    abstract fun noteDAO():NoteDAO
    companion object{
        const val DATABASE_NAME = "db_notes"
    }
}