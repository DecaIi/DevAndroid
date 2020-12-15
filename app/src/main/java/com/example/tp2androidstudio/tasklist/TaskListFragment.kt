package com.example.tp2androidstudio.tasklist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
import okhttp3.internal.notify

class TaskListFragment : Fragment() {

    private val adapter = TaskListAdapter()
    private lateinit var myTextView: TextView;

    private val viewModel: TaskListViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_task_list, container, false);
        return rootView;
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            val userInfo = Api.userService.getInfo().body()!!
            myTextView.text = "${userInfo.firstName} ${userInfo.lastName}"
        }
        viewModel.loadTasks()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        myTextView = view.findViewById<TextView>(R.id.User_Info)

        var recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview);
        recyclerView.layoutManager = LinearLayoutManager(activity);
        recyclerView.adapter = adapter

        adapter.onDeleteClickListener = { task -> viewModel.deleteTask(task) }

        val buton = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        buton.setOnClickListener {
            val intent = Intent(activity, TaskActivity::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST_CODE)
        }
        viewModel.taskList.observe(viewLifecycleOwner) { newList ->
            adapter.submitList(newList);
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ADD_TASK_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val task = data!!.getSerializableExtra(TaskActivity.TASK_KEY) as Task

            viewModel.addTask(task)
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
