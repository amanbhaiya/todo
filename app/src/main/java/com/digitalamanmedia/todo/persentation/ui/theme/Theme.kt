package com.digitalamanmedia.todo.persentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color.White,
    background = Dark_Grey,
    onBackground = Color.White,
    surface = Light_Blue,
    onSurface = Dark_Grey
)



@Composable
fun ToDoTheme(darkTheme: Boolean = true, content: @Composable() () -> Unit) {


    MaterialTheme(
        colors = DarkColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}