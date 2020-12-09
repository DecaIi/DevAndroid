package com.example.tp2androidstudio.tasklist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp2androidstudio.R
import com.example.tp2androidstudio.network.Api
import com.example.tp2androidstudio.network.TasksRepository
import com.example.tp2androidstudio.task.TaskActivity
import com.example.tp2androidstudio.task.TaskActivity.Companion.ADD_TASK_REQUEST_CODE
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class TaskListFragment : Fragment() {
    private val taskList = mutableListOf(
            Task(id = "id_1", title = "Task 1", description = "description 1"),
            Task(id = "id_2", title = "Task 2"),
            Task(id = "id_3", title = "Task 3")
    )
    private val adapter = TaskListAdapter(taskList)
    private lateinit var myTextView : TextView;
    private val tasksRepository = TasksRepository()


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_task_list, container, false);
        return rootView;
    }
    override fun onResume(){
        super.onResume()
        lifecycleScope.launch{
            val userInfo = Api.userService.getInfo().body()!!
            myTextView.text ="${userInfo.firstName} ${userInfo.lastName}"
            tasksRepository.refresh()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        myTextView = view.findViewById<TextView>(R.id.User_Info)

        var recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview);
        recyclerView.layoutManager = LinearLayoutManager(activity);
        recyclerView.adapter = adapter
        /*previouse deleteClickLitener
        adapter.onDeleteClickListener = { task ->
            taskList.remove(task);
            adapter.notifyDataSetChanged()
        };
        */
        adapter.onDeleteClickListener = { task ->
            lifecycleScope.launch {
                tasksRepository.delete(task)
            }
        }

        val buton = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        buton.setOnClickListener {
            val intent = Intent(activity, TaskActivity::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST_CODE)
        }
        tasksRepository.taskList.observe(viewLifecycleOwner, Observer {
            taskList.clear()    // remove adapter.
            taskList.addAll(it) // remove adapter.
            adapter.notifyDataSetChanged()
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val task = data!!.getSerializableExtra(TaskActivity.TASK_KEY) as Task
        taskList.add(task);
        adapter.notifyDataSetChanged()
    }
}
