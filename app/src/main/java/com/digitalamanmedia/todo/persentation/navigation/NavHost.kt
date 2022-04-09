package com.digitalamanmedia.todo.persentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.digitalamanmedia.todo.persentation.notes.screen.NoteScreen
import com.digitalamanmedia.todo.persentation.notes_edit_details.screen.NoteDetailScreen

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalAnimationApi
@Composable
fun NavHostControl(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Screens.NoteScreen.route
    ){
        composable(Screens.NoteScreen.route){
            NoteScreen(navController = navController)
        }
        composable(
            route = Screens.NoteDetailScreen.route +
                "?noteId={noteId}&noteColor={noteColor}",
            arguments = listOf(
                navArgument(
                    name = "noteId"
                ){
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument(
                    name = "noteColor"
                ){
                    type = NavType.IntType
                    defaultValue = -1
                },
            )
        ){
            val color = it.arguments?.getInt("noteColor")?: -1
            NoteDetailScreen(
                navController = navController,
                noteColor = color
            )
        }
    }

}