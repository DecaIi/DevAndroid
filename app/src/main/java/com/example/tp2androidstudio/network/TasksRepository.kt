package com.example.tp2androidstudio.network

import com.example.tp2androidstudio.tasklist.Task
import retrofit2.Response

class TasksRepository {
    private val tasksWebService = Api.tasksWebService

    suspend fun delete(task: Task): Response<String> {
        return tasksWebService.deleteTask(task.id)
    }
    suspend fun updateTask(task :Task): Response<Task> {
        return tasksWebService.updateTask(task)
    }
    suspend fun createTask(task: Task): Response<Task> {
        return tasksWebService.createTask(task)
    }

    suspend fun loadTasks(): List<Task>? {
        val response = tasksWebService.getTasks()
        return if (response.isSuccessful) response.body() else null
    }


}