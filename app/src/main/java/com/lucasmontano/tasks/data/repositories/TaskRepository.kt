package com.lucasmontano.tasks.data.repositories

import androidx.lifecycle.Transformations
import com.lucasmontano.tasks.data.dao.TaskDao
import com.lucasmontano.tasks.data.mappers.toDomainModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

    fun getAllTasks() = Transformations.map(taskDao.getAllTasks()) { tasks ->
        tasks.map {
            it.toDomainModel()
        }
    }
}
