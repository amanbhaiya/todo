package com.digitalamanmedia.todo.domain.modal

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.digitalamanmedia.todo.persentation.ui.theme.*
import java.lang.Exception

@Entity(
    tableName = "notes"
)
data class Notes(
    val title:String,
    val content:String,
    val time:Long,
    val color:Int,
    @PrimaryKey(autoGenerate = true) val id:Int? = null
){
    companion object{
        var noteColors = listOf(
            Red_Orange, Light_Green, Baby_Blue, Voilet, Red_Pink
        )
    }
}
class InvalidNoteException(message: String): Exception(message)
