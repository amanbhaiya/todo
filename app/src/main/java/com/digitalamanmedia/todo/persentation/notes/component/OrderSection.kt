package com.digitalamanmedia.todo.persentation.notes.component

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.digitalamanmedia.todo.domain.utils.NoteOrder
import com.digitalamanmedia.todo.domain.utils.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChanged:(NoteOrder) -> Unit
) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
            CustomRadio(
                text = "Title" ,
                selected = noteOrder is NoteOrder.Title,
                onSelected = {
                    onOrderChanged(NoteOrder.Title(noteOrder.orderType))
                }
            )
            Spacer(modifier = Modifier.width(12.dp))
            CustomRadio(
                text = "Date" ,
                selected = noteOrder is NoteOrder.Date,
                onSelected = {
                    onOrderChanged(NoteOrder.Date(noteOrder.orderType))
                }
            )
            Spacer(modifier = Modifier.width(12.dp))
            CustomRadio(
                text = "Color" ,
                selected = noteOrder is NoteOrder.Color,
                onSelected = {
                    onOrderChanged(NoteOrder.Color(noteOrder.orderType))
                }
            )



        }

        Row(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
            CustomRadio(
                text = "Ascending" ,
                selected = noteOrder.orderType is OrderType.Ascending,
                onSelected = {
                    onOrderChanged(noteOrder.copy(OrderType.Ascending))
                }
            )
            Spacer(modifier = Modifier.width(12.dp))
            CustomRadio(
                text = "Descending" ,
                selected = noteOrder.orderType is OrderType.Descending,
                onSelected = {
                    onOrderChanged(noteOrder.copy(OrderType.Descending))
                }
            )

        }
    }

}