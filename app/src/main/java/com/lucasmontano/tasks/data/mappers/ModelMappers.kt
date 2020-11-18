package com.lucasmontano.tasks.data.mappers

import com.lucasmontano.tasks.data.domain.TaskDomain
import com.lucasmontano.tasks.data.entities.TaskEntity

fun TaskEntity.toDomainModel() = TaskDomain(title = this.title)
