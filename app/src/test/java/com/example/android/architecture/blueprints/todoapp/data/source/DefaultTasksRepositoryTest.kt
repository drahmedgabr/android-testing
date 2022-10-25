package com.example.android.architecture.blueprints.todoapp.data.source

import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.hamcrest.core.IsEqual
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DefaultTasksRepositoryTest {

    private val task1 = Task("Task1", "Description1")
    private val task2 = Task("Task2", "Description2")
    private val task3 = Task("Task3", "Description3")

    private val localTasks = listOf<Task>(task1, task2).sortedBy { it.id }
    private val remoteTasks = listOf<Task>(task3).sortedBy { it.id }
    private val newTasks = listOf<Task>(task3).sortedBy { it.id }

    private lateinit var tasksRepository: DefaultTasksRepository
    private lateinit var tasksLocalDataSource: FakeDataSource
    private lateinit var tasksRemoteDataSource: FakeDataSource

    @Before
    fun createRepository() {
        tasksLocalDataSource = FakeDataSource(localTasks.toMutableList())
        tasksRemoteDataSource = FakeDataSource(remoteTasks.toMutableList())

        tasksRepository = DefaultTasksRepository(
            tasksRemoteDataSource, tasksLocalDataSource, Dispatchers.Unconfined
        )
    }

    @Test
    fun newTest() = runTest {
        val tasks =
            tasksRepository.getTasks(true) as Result.Success

        assertThat(tasks.data, IsEqual(remoteTasks))
    }
}