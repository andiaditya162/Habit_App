package com.dityapra.habittracker.ui.list

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.dityapra.habitapp.R
import com.dityapra.habitapp.ui.list.HabitListActivity

@RunWith(AndroidJUnit4ClassRunner::class)
class HabitActivityTest {
    @Before
    fun setUp() {
        ActivityScenario.launch(HabitListActivity::class.java)
    }

    @Test
    fun testTapAddHabit_openAddHabitActivity() {
        onView(withId(R.id.fab)).perform(click())
        onView(withId(R.id.toolbar)).check(doesNotExist())
        onView(withId(R.id.add_ed_title)).check(matches(isDisplayed()))
        onView(withId(R.id.add_ed_minutes_focus)).check(matches(isDisplayed()))
        onView(withId(R.id.sp_priority_level)).check(matches(isDisplayed()))
        onView(withId(R.id.action_save)).check(matches(isDisplayed()))
    }
}