package com.example.tp2androidstudio.task

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tp2androidstudio.R
import com.example.tp2androidstudio.tasklist.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class TaskActivity :Activity () {
    companion object {
        const val TASK_KEY: String = "new task"
        const val ADD_TASK_REQUEST_CODE = 666
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.task_editor)
        var buton  = findViewById<ImageButton>(R.id.validateButton)
        var title = findViewById<EditText>(R.id.task_activity_title).text.toString()
        var descrption = findViewById<EditText>(R.id.task_activity_description).text.toString()
        buton.setOnClickListener { val newTask = Task(id = UUID.randomUUID().toString(), title = title,description = descrption ) ;
            intent?.putExtra(TASK_KEY,newTask)
            setResult(ADD_TASK_REQUEST_CODE,intent)
            finish()
        }
    }
}