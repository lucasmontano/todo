package com.lucasmontano.tasks.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.lucasmontano.tasks.data.domain.TaskDomain
import com.lucasmontano.tasks.data.repositories.TaskRepository
import com.lucasmontano.tasks.ui.models.TaskUiModel

class TaskListViewModel @ViewModelInject internal constructor(
    taskRepository: TaskRepository
) : ViewModel() {

    private val filter = MutableLiveData<FilterState>()

    private val tasks: LiveData<List<TaskUiModel>> = Transformations.switchMap(filter) {
        when (filter.value) {
            is FilterState.ToDo -> {
                Transformations.map(taskRepository.getAllTasks()) { tasks ->
                    tasks.toUiModel()
                }
            }
            is FilterState.Done -> {
                Transformations.map(taskRepository.getAllTasks()) { tasks ->
                    tasks.toUiModel()
                }
            }
            else -> {
                null
            }
        }
    }

    val uiState: LiveData<ViewState> = Transformations.switchMap(tasks) {
        MutableLiveData(ViewState(it))
    }

    init {
        filter.postValue(FilterState.ToDo)
    }

    private fun List<TaskDomain>.toUiModel(): List<TaskUiModel> {
        return this.map {
            TaskUiModel(title = it.title)
        }
    }

    data class ViewState(val tasks: List<TaskUiModel>)

    private sealed class FilterState {
        object Done : FilterState()
        object ToDo : FilterState()
    }
}