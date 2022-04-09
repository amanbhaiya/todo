package com.digitalamanmedia.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface

import androidx.navigation.compose.rememberNavController
import com.digitalamanmedia.todo.persentation.navigation.NavHostControl
import com.digitalamanmedia.todo.persentation.ui.theme.ToDoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            ToDoTheme {
                Surface(
                    color = MaterialTheme.colors.primary
                ) {
                    val navController = rememberNavController()
                    NavHostControl(navController = navController)
                }
            }
        }
    }
}


