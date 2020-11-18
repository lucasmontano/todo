package com.lucasmontano.tasks.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.lucasmontano.tasks.utilities.DATABASE_TABLE_TASKS

@Entity(tableName = DATABASE_TABLE_TASKS)
data class TaskEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val taskId: String,
    val title: String
) {

    override fun toString() = title
}
