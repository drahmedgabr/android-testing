package com.example.android.architecture.blueprints.todoapp.statistics

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.Event
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.DefaultTasksRepository
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTasksRepository
import com.example.android.architecture.blueprints.todoapp.tasks.TasksViewModel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsNot.not
import org.hamcrest.core.IsNull.nullValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


class StatisticsUtilsTest {

    // Subject under test
    private lateinit var taskViewModel: TasksViewModel
    private lateinit var tasksRepository: FakeTasksRepository

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        tasksRepository = FakeTasksRepository()

        val task1 = Task("Task1", "Description1")
        val task2 = Task("Task2", "Description2")
        val task3 = Task("Task3", "Description3")

        tasksRepository.addTasks(task1, task2, task3)

        taskViewModel = TasksViewModel(tasksRepository)
    }

    @Test
    fun newTest() {
        val tasks = listOf<Task>(
            Task("Title", "Description", false)
        )

        val result = getActiveAndCompletedStats(tasks)

        assertEquals(0f, result.completedTasksPercent)
        assertEquals(100f, result.activeTasksPercent)
    }


    @Test
    fun viewModelTest() {

//        val observer = Observer<Event<Unit>>{}
//
//        try {
//            taskViewModel.newTaskEvent.observeForever(observer)
//            taskViewModel.addNewTask()
//            val value = taskViewModel.newTaskEvent.value
//
//            assertThat(value?.getContentIfNotHandled(), not(nullValue()))
//        } finally {
//            taskViewModel.newTaskEvent.removeObserver(observer)
//        }

        taskViewModel.addNewTask()

        val value = taskViewModel.newTaskEvent.getOrAwaitValue()

        var nvalue = 0

        runTest {
            val result = tasksRepository.getTasks(true) as Result.Success
            nvalue = result.data.size
        }

        assertThat(
            value.getContentIfNotHandled(), (not(nullValue()))
        )
        assertThat(
            nvalue, IsEqual(3)
        )
    }

}