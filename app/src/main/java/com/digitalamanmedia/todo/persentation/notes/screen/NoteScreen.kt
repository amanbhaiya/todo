package com.digitalamanmedia.todo.persentation.notes.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.digitalamanmedia.todo.domain.modal.Notes
import com.digitalamanmedia.todo.persentation.navigation.Screens
import com.digitalamanmedia.todo.persentation.notes.component.NoteItem
import com.digitalamanmedia.todo.persentation.notes.component.OrderSection
import com.digitalamanmedia.todo.persentation.notes.viewmodel.NoteUiEvent
import com.digitalamanmedia.todo.persentation.notes.viewmodel.NotesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalAnimationApi
@Composable
fun NoteScreen(
   navController: NavController,
   viewModel: NotesViewModel = hiltViewModel()

) {
   val state = viewModel.state.value
   val scaffoldState = rememberScaffoldState()
   val scope = rememberCoroutineScope()

   Scaffold(
      floatingActionButton = {
         FloatingActionButton(
            onClick = {
                     navController.navigate(Screens.NoteDetailScreen.route)
            },
            backgroundColor = MaterialTheme.colors.primary

         ){
            Icon(
               imageVector = Icons.Default.Add,
               contentDescription = "Add Note"
            )
         }


      },
      scaffoldState = scaffoldState
   ) {
      Column(
         modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
      ) {
         Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
         ) {
            Text(
               text = "Your Notes",
               style = MaterialTheme.typography.h4
            )
            IconButton(
               onClick = {
                  viewModel.onEvent(NoteUiEvent.ToggleOrderSection)
               }
            ) {
               Icon(
                  imageVector = Icons.Default.Sort,
                  contentDescription = "Sort"
               )

            }
         }
         Spacer(modifier = Modifier.padding(vertical = 8.dp))
         AnimatedVisibility(
            visible = state.isToggleButtonsVisible,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
         ) {
            OrderSection(
               onOrderChanged ={
                       viewModel.onEvent(NoteUiEvent.Order(it))
               },
               modifier = Modifier.fillMaxWidth(),
               noteOrder = state.noteOrder

            )
         }
         Spacer(modifier = Modifier.height(16.dp))
         LazyColumn(Modifier.fillMaxSize()){



            items(state.notes) {note ->
               NoteItem(
                  notes = note,
                  modifier = Modifier
                     .fillMaxWidth().padding(vertical = 8.dp)
                     .clickable {
                        navController.navigate(
                           Screens.NoteDetailScreen.route +
                                   "?noteId=${note.id}&noteColor=${note.color}"
                        )
                     },
                  onDeleteClick = {
                     viewModel.onEvent(NoteUiEvent.DeleteNote(note))
                     scope.launch {
                        val result = scaffoldState.snackbarHostState.showSnackbar(
                           message = "Note Deleted",
                           actionLabel = "Undo"
                        )
                        if(result == SnackbarResult.ActionPerformed){
                           viewModel.onEvent(NoteUiEvent.RestoreNote)
                        }
                     }
                  }
               )
            }
         }
      }
   }
}

