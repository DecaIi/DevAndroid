package com.example.tp2androidstudio.tasklist

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tp2androidstudio.R

class TaskListAdapter(private val taskList: List<Task>) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(task: Task) {
            itemView.apply { // `apply {}` permet d'éviter de répéter `itemView.*`
                var myTextView = findViewById<TextView>(R.id.task_title)
                var decscrition =  findViewById<TextView>(R.id.task_description)
                myTextView.text = task.title ;
                decscrition.text = task.description;

                findViewById<ImageButton>(R.id.deletButton).setOnClickListener { onDeleteClickListener?.invoke(task) }
            }
        }
    }
    var onDeleteClickListener : ((Task) -> Unit)? =null ;


    override fun getItemCount(): Int {
        return taskList.size;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView);
    }


    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position]);
    }


}