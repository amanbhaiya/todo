package com.digitalamanmedia.todo.persentation.navigation

sealed class Screens(val route: String){
    object NoteScreen: Screens("Note")
    object NoteDetailScreen: Screens("NoteDetail")
}
