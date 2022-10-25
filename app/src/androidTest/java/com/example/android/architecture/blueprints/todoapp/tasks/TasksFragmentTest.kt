package com.example.android.architecture.blueprints.todoapp.tasks

import FakeAndroidTasksRepository
import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.ServiceLocator
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class TasksFragmentTest {

    private lateinit var tasksRepository: TasksRepository

    @Before
    fun init(){
        tasksRepository = FakeAndroidTasksRepository()
        ServiceLocator.tasksRepository = tasksRepository
    }

    @After
    fun clean(){
        ServiceLocator.resetRepository()
    }

    @Test
    fun newTest() = runBlocking {

        tasksRepository.saveTask(Task("Task1", "Desc1", false, "id1"))
        tasksRepository.saveTask(Task("Task2", "Desc2", false, "id2"))

        val scenario = launchFragmentInContainer<TasksFragment>(Bundle(), R.style.AppTheme)

        val navController = mock(NavController::class.java)

        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        onView(withId(R.id.tasks_list)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
            hasDescendant(withText("Task1")), click()
        ))

        verify(navController).navigate(TasksFragmentDirections.actionTasksFragmentToTaskDetailFragment("id1"))

        Thread.sleep(3000)
    }

}