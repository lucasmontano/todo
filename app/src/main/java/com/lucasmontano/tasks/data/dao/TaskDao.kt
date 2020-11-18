package com.lucasmontano.tasks.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lucasmontano.tasks.data.entities.TaskEntity
import com.lucasmontano.tasks.utilities.DATABASE_TABLE_TASKS

@Dao
interface TaskDao {
    @Query("SELECT * FROM $DATABASE_TABLE_TASKS ORDER BY title")
    fun getAllTasks(): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM $DATABASE_TABLE_TASKS WHERE id = :taskId")
    fun getTask(taskId: String): LiveData<TaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(taskList: List<TaskEntity>)
}
