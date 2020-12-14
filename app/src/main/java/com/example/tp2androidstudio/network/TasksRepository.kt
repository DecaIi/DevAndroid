package com.example.tp2androidstudio.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tp2androidstudio.tasklist.Task

class TasksRepository {
    private val tasksWebService = Api.tasksWebService

    // Ces deux variables encapsulent la même donnée:
    // [_taskList] est modifiable mais privée donc inaccessible à l'extérieur de cette classe
    private val _taskList = MutableLiveData<List<Task>>()

    // [taskList] est publique mais non-modifiable:
    // On pourra seulement l'observer (s'y abonner) depuis d'autres classes
    public val taskList: LiveData<List<Task>> = _taskList

    suspend fun delete(task: Task) { // in dev
        // Call HTTP (opération longue):
        val tasksResponse = tasksWebService.deleteTask(task.id)
        // À la ligne suivante, on a reçu la réponse de l'API:
        if (tasksResponse.isSuccessful) {
            val editableList = taskList.value.orEmpty().toMutableList()
            editableList.remove(task)
            _taskList.value = editableList
        }
    }

    suspend fun createTask(task: Task) {
        val tasksResponse = tasksWebService.createTask(task)
        if (tasksResponse.isSuccessful) {
            val createdTask = tasksResponse.body()
            val editableList = _taskList.value.orEmpty().toMutableList()
            editableList.add(createdTask!!)
            _taskList.value = editableList
        }
    }

    suspend fun refresh() {
        // Call HTTP (opération longue):
        val tasksResponse = tasksWebService.getTasks()
        // À la ligne suivante, on a reçu la réponse de l'API:
        if (tasksResponse.isSuccessful) {
            val fetchedTasks = tasksResponse.body()
            // on modifie la valeur encapsulée, ce qui va notifier ses Observers et donc déclencher leur callback
            _taskList.value = fetchedTasks!!
        }
    }


}