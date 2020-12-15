package com.example.tp2androidstudio.tasklist

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tp2androidstudio.R

object TaskListDiffCallBack: DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem;
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return  oldItem.id == newItem.id && oldItem.title == newItem.title && oldItem.description == newItem.description ;
    }

}

class TaskListAdapter() : ListAdapter< Task, TaskListAdapter.TaskViewHolder>(TaskListDiffCallBack) {
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(task: Task) {
            itemView.apply { // `apply {}` permet d'éviter de répéter `itemView.*`
                var textView = findViewById<TextView>(R.id.task_title)
                var decscrition = findViewById<TextView>(R.id.task_description)
                textView.text = task.title;
                decscrition.text = task.description;
                findViewById<ImageButton>(R.id.deletButton).setOnClickListener { onDeleteClickListener?.invoke(task) }
            }
        }
    }

    var onDeleteClickListener: ((Task) -> Unit)? = null;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView);
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(currentList[position]);
    }
}
