package com.example.tp2androidstudio.tasklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp2androidstudio.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.UUID.randomUUID

class TaskListFragment : Fragment()
{
    private val taskList = mutableListOf(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2"),
        Task(id = "id_3", title = "Task 3")
    )


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_task_list, container, false);



        return rootView;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview);
        recyclerView.layoutManager= LinearLayoutManager(activity);
        recyclerView.adapter = TaskListAdapter(taskList);
        val buton = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        buton.setOnClickListener {taskList.add(Task(id = randomUUID().toString(), title = "Task ${taskList.size + 1}"));(recyclerView.adapter as TaskListAdapter).notifyDataSetChanged() }
    }

}
