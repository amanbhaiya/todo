package com.digitalamanmedia.todo.persentation.notes_edit_details.screen

import android.print.PrintAttributes
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.digitalamanmedia.todo.domain.modal.Notes
import com.digitalamanmedia.todo.persentation.notes_edit_details.component.TransparentTextField
import com.digitalamanmedia.todo.persentation.notes_edit_details.viewmodel.AddEditNoteDetailEvent
import com.digitalamanmedia.todo.persentation.notes_edit_details.viewmodel.AddEditNoteViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@Composable
fun NoteDetailScreen(
    navController: NavController,
    noteColor: Int,
    viewModel:AddEditNoteViewModel = hiltViewModel()
) {
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value
    val scaffoldState = rememberScaffoldState()
    val backGroundAnimatable = remember {
        Animatable(
            Color(
                if (noteColor != -1){
                    noteColor

                }else{
                    viewModel.noteColor.value
                }
            )
        )

    }
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = true,){
        viewModel.eventFlow.collectLatest { event->
            when(event){
               is AddEditNoteViewModel.UiEvent.ShowSnackBar->{
                   scaffoldState.snackbarHostState.showSnackbar(
                       message = event.message
                   )
               }
                is AddEditNoteViewModel.UiEvent.SaveNote->{
                    navController.popBackStack()
                }
            }

        }
    }

    Scaffold(
        floatingActionButton = {
        FloatingActionButton(
            onClick = {
                viewModel.onAddEvent(AddEditNoteDetailEvent.SaveNote)
            },
            backgroundColor = MaterialTheme.colors.primary
        ) {
            Icon(imageVector = Icons.Default.Save, contentDescription = "Save")
          }
        },
        scaffoldState = scaffoldState
    ) {
       Column(modifier = Modifier
           .fillMaxSize()
           .background(backGroundAnimatable.value)
           .padding(16.dp)
       ) {
           Row(modifier = Modifier
               .fillMaxWidth()
               .padding(8.dp),
               horizontalArrangement = Arrangement.SpaceBetween
           ) {
               Notes.noteColors.forEach { color->
                   val colorInt = color.toArgb()
                   Box(
                       modifier = Modifier
                           .size(50.dp)
                           .shadow(15.dp, CircleShape)
                           .clip(CircleShape)
                           .background(color = color)
                           .border(
                               width = 3.dp,
                               color = if (viewModel.noteColor.value == colorInt) {
                                   Black
                               } else Transparent,
                               shape = CircleShape

                           )
                           .clickable {
                               scope.launch {
                                   backGroundAnimatable.animateTo(
                                       targetValue = Color(colorInt),
                                       animationSpec = tween(
                                           durationMillis = 500
                                       )
                                   )
                               }
                               viewModel.onAddEvent(AddEditNoteDetailEvent.ChangeColor(colorInt))
                           }
                   )

               }
           }
           Spacer(modifier = Modifier.height(16.dp))
           TransparentTextField(
               text = titleState.text,
               hint = titleState.hint,
               onValueChanged = {
                                viewModel.onAddEvent(AddEditNoteDetailEvent.EnteredTitle(it))
               },
               onFocusChange ={
                   viewModel.onAddEvent(AddEditNoteDetailEvent.ChangeTitleFocus(it))
               },
               isHintVisible = titleState.isHintVisible,
               singleLine = true,
               textStyle = MaterialTheme.typography.h4
           )
           Spacer(modifier = Modifier.padding(vertical = 10.dp))
           TransparentTextField(
               text = contentState.text,
               hint = contentState.hint,
               onValueChanged = {
                   viewModel.onAddEvent(AddEditNoteDetailEvent.EnteredContent(it))
               },
               onFocusChange ={
                   viewModel.onAddEvent(AddEditNoteDetailEvent.ChangeContentFocus(it))
               },
               isHintVisible = contentState.isHintVisible,
               textStyle = MaterialTheme.typography.body1,
               modifier = Modifier.fillMaxHeight()
           )

       }
    }


}