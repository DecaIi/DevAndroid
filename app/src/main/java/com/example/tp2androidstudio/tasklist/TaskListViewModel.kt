package com.example.tp2androidstudio.tasklist

import androidx.lifecycle.*
import com.example.tp2androidstudio.network.TasksRepository
import kotlinx.coroutines.launch

class TaskListViewModel : ViewModel() {
    private val repository = TasksRepository()
    private val _taskList = MutableLiveData<List<Task>>()
    val taskList: LiveData<List<Task>> = _taskList

    fun loadTasks() {
        viewModelScope.launch { _taskList.value = repository.loadTasks() }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            val tasksResponse = repository.delete(task)
            if (tasksResponse.isSuccessful) {
                val editableList = taskList.value.orEmpty().toMutableList()
                editableList.remove(task)
                _taskList.value = editableList
            }
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            val tasksResponse = repository.createTask(task)
            if (tasksResponse.isSuccessful) {
                val editableList = taskList.value.orEmpty().toMutableList()
                editableList.remove(tasksResponse.body())
                _taskList.value = editableList
            }
        }

    }

    fun editTask(task: Task) {

    }

}