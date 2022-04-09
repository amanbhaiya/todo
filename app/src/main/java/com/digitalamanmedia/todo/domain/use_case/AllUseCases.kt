package com.digitalamanmedia.todo.domain.use_case

data class AllUseCases (
    val getNotesUseCase: GetNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val insertNoteUseCase: InsertNoteUseCase,
    val getNoteById: GetNoteById

        )