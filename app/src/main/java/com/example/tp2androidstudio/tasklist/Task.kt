package com.example.tp2androidstudio.tasklist

import androidx.recyclerview.widget.DiffUtil
import kotlinx.serialization.SerialName
import java.io.Serializable
@kotlinx.serialization.Serializable
data class Task(
        @SerialName("id")
        val id:String,
        @SerialName("title")
        var title:String,
        @SerialName("description")
        var description : String ="no descirption"):Serializable{

}
