package com.lucasmontano.tasks.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.lucasmontano.tasks.data.domain.TaskDomain
import com.lucasmontano.tasks.data.repositories.TaskRepository
import com.lucasmontano.tasks.getValueUnitTest
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TaskListViewModelTest {

    @Rule
    @JvmField
    val instantExecutor = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    lateinit var repository: TaskRepository

    private lateinit var viewModel: TaskListViewModel

    private val taskA = TaskDomain(
        "Task A"
    )
    private val taskB = TaskDomain(
        "Task B"
    )
    private val taskC = TaskDomain(
        "Task C"
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        viewModel = TaskListViewModel(repository)

        every {
            repository.getAllTasks()
        } returns MutableLiveData(
            listOf(
                taskA, taskB, taskC
            )
        )
    }

    @Test
    @Throws(InterruptedException::class)
    fun `when initializing the viewmodel, ToDo tasks are listed`() {
        assertTrue(getValueUnitTest(viewModel.uiState).tasks.isNotEmpty())
    }
}
