package com.example.android.architecture.blueprints.todoapp.statistics

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.Event
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.tasks.TasksViewModel
import org.hamcrest.core.IsNot.not
import org.hamcrest.core.IsNull.nullValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(sdk = [29])
@RunWith(AndroidJUnit4::class)
class StatisticsUtilsTest {

    // Subject under test
    private lateinit var taskViewModel: TasksViewModel

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        taskViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun newTest(){
        val tasks = listOf<Task>(
            Task("Title", "Description", false)
        )

        val result = getActiveAndCompletedStats(tasks)

        assertEquals(0f, result.completedTasksPercent)
        assertEquals(100f, result.activeTasksPercent)
    }


    @Test
    fun viewModelTest(){

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
        assertThat(
            value.getContentIfNotHandled(), (not(nullValue()))
        )
    }




}