package com.digitalamanmedia.todo.di

import android.app.Application
import androidx.room.Room
import com.digitalamanmedia.todo.data.data_source.NoteDAO
import com.digitalamanmedia.todo.data.data_source.NoteDataBase
import com.digitalamanmedia.todo.data.repository.NoteRepositoryImpl
import com.digitalamanmedia.todo.domain.repository.NotesRepository
import com.digitalamanmedia.todo.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNotesDatabase(app: Application): NoteDataBase{
        return Room.databaseBuilder(
            app,
            NoteDataBase::class.java,
            NoteDataBase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteDAO(noteDataBase: NoteDataBase): NoteDAO{
       return noteDataBase.noteDAO()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(noteDAO: NoteDAO):NotesRepository{
        return NoteRepositoryImpl(noteDAO = noteDAO)
    }

    @Provides
    @Singleton
    fun provideAllUseCases(notesRepository: NotesRepository): AllUseCases{
        return AllUseCases(
            getNotesUseCase = GetNotesUseCase(notesRepository),
            deleteNoteUseCase = DeleteNoteUseCase(notesRepository),
            insertNoteUseCase = InsertNoteUseCase(notesRepository),
            getNoteById = GetNoteById(notesRepository)

        )
    }
}

