package com.lucasmontano.tasks.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.lucasmontano.tasks.data.dao.TaskDao
import com.lucasmontano.tasks.data.entities.TaskEntity
import com.lucasmontano.tasks.utilities.getValue
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TaskDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var dao: TaskDao

    private val taskA = TaskEntity("a", "Task A")
    private val taskB = TaskEntity("b", "Task B")
    private val taskC = TaskEntity("c", "Task C")

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = database.taskDao()
        dao.insertAll(listOf(taskA, taskB, taskC))
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetAllTasks() {
        val taskList = getValue(dao.getAllTasks())
        assertThat(taskList.size, equalTo(3))
        assertThat(taskList[0], equalTo(taskA))
        assertThat(taskList[1], equalTo(taskB))
        assertThat(taskList[2], equalTo(taskC))
    }

    @Test
    fun testGetTask() {
        assertThat(getValue(dao.getTask(taskA.taskId)), equalTo(taskA))
    }
}
