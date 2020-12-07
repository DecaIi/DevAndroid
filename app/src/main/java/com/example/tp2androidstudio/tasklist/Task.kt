package com.example.tp2androidstudio.tasklist

import java.io.Serializable

data class Task(val id:String,var title:String, var description : String ="no descirption"):Serializable{


}