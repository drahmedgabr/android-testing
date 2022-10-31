package com.example.android.architecture.blueprints.todoapp.tasks

import FakeAndroidTasksRepository
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.Event
import com.example.android.architecture.blueprints.todoapp.LoggerE
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class TasksViewModelTest {

    private lateinit var tasksViewModel: TasksViewModel
    private lateinit var tasksRepository: FakeAndroidTasksRepository
    val testDispatcher = StandardTestDispatcher()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init(){
        tasksRepository = FakeAndroidTasksRepository()
        tasksViewModel = TasksViewModel(tasksRepository)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun onRelease(){
        Dispatchers.resetMain()
    }

    @Test
    fun completeTask() {

        val task = Task("Title", "Description")
        tasksRepository.addTasks(task)
        tasksViewModel.completeTask(task, true)

         assertThat(tasksRepository.tasksServiceData[task.id]?.isCompleted, `is`(true))

        val snackbarText: Event<Int> =  tasksViewModel.snackbarText.value!!
        assertThat(snackbarText.getContentIfNotHandled(), `is`(R.string.task_marked_complete))
    }
}