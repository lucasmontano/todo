package com.lucasmontano.tasks.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.stream.JsonReader
import com.lucasmontano.tasks.data.AppDatabase
import com.lucasmontano.tasks.data.entities.TaskEntity
import com.lucasmontano.tasks.utilities.DATA_FILENAME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


class InitDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {
        try {
            val tasks: List<TaskEntity> = withContext(Dispatchers.IO) {
                applicationContext.assets.open(DATA_FILENAME).use { inputStream ->
                    readJsonStream(inputStream)
                }
            }

            val database = AppDatabase.getInstance(applicationContext)
            database.taskDao().insertAll(tasks)

            Result.success()
        } catch (ex: Exception) {
            Log.e(TAG, "Error initializing database", ex)
            Result.failure()
        }
    }

    @Throws(IOException::class)
    fun readJsonStream(`in`: InputStream?): List<TaskEntity> {
        val reader = JsonReader(InputStreamReader(`in`, "UTF-8"))
        return reader.use {
            readMessagesArray(it)
        }
    }

    @Throws(IOException::class)
    fun readMessagesArray(reader: JsonReader): List<TaskEntity> {
        val messages: MutableList<TaskEntity> = ArrayList()
        reader.beginArray()
        while (reader.hasNext()) {
            messages.add(readMessage(reader))
        }
        reader.endArray()
        return messages
    }

    @Throws(IOException::class)
    fun readMessage(reader: JsonReader): TaskEntity {
        var id = ""
        var title = ""
        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.nextName()) {
                "id" -> {
                    id = reader.nextString()
                }
                "title" -> {
                    title = reader.nextString()
                }
                else -> {
                    reader.skipValue()
                }
            }
        }
        reader.endObject()
        return TaskEntity(taskId = id, title = title)
    }

    companion object {
        private val TAG = this::class.simpleName
    }
}
