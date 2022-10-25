package com.example.android.architecture.blueprints.todoapp

import FakeAndroidTasksRepository
import androidx.fragment.app.testing.launchFragmentInContainer
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import com.example.android.architecture.blueprints.todoapp.taskdetail.TaskDetailFragment
import com.example.android.architecture.blueprints.todoapp.taskdetail.TaskDetailFragmentArgs
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isChecked
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.core.IsNot.not

class TaskDetailFragmentTest {
    private lateinit var tasksRepository: TasksRepository

    @Before
    fun initRepository(){
        tasksRepository = FakeAndroidTasksRepository()

        ServiceLocator.tasksRepository = tasksRepository
    }

    @After
    fun resetRepository() = runBlocking {
        ServiceLocator.resetRepository()
    }

    @Test
    fun newTest() = runBlocking {
        val activeTask = Task("New Task", "Task details...", false)

        tasksRepository.saveTask(activeTask)

        val bundle = TaskDetailFragmentArgs(activeTask.id).toBundle()

        launchFragmentInContainer<TaskDetailFragment>(bundle, R.style.AppTheme)

        onView(withId(R.id.task_detail_title_text)).check(matches(isDisplayed()))
        onView(withId(R.id.task_detail_title_text)).check(matches(withText("New Task")))
        onView(withId(R.id.task_detail_description_text)).check(matches(isDisplayed()))
        onView(withId(R.id.task_detail_description_text)).check(matches(withText("Task details...")))
        onView(withId(R.id.task_detail_complete_checkbox)).check(matches(isDisplayed()))
        onView(withId(R.id.task_detail_complete_checkbox)).check(matches(not(isChecked())))

        Thread.sleep(3000)
    }

}