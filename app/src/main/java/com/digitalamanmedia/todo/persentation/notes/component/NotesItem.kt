package com.digitalamanmedia.todo.persentation.notes.component


import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.digitalamanmedia.todo.domain.modal.Notes
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteItem(
    modifier: Modifier = Modifier,
    notes: Notes,
    cornerRadius: Dp = 10.dp,
    cutCornerRadius: Dp =30.dp,
    onDeleteClick: () -> Unit
) {


    val dateTime = SimpleDateFormat("dd-MMMM-yyyy, HH:mm")
    val date = dateTime.format(notes.time)

    Box(
        modifier = modifier
    ) {
        Canvas(modifier = Modifier.matchParentSize()){
            val clipPath = Path().apply {
                lineTo(size.width - cutCornerRadius.toPx(),0f)
                lineTo(size.width, cutCornerRadius.toPx())
                lineTo(size.width,size.height)
                lineTo(0f,size.height)
            }
            clipPath(clipPath){
                drawRoundRect(
                    color = Color(notes.color),
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
                drawRoundRect(
                    color = Color(
                        ColorUtils.blendARGB(notes.color,0x000000,0.2f)
                    ),
                    topLeft = Offset(size.width - cutCornerRadius.toPx(),-100f),
                    size = Size(cutCornerRadius.toPx() + 100f, cutCornerRadius.toPx() +100f),
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
            }
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 12.dp, 16.dp, 16.dp)
            .padding(end = 32.dp)) {
            Text(
                text = date,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface,
            )
            Divider(color = Color.DarkGray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = notes.title,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = notes.content,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
                maxLines = 6,
                overflow = TextOverflow.Clip
            )
        }


        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Note",
                tint = Color.DarkGray
            )

        }

    }



}