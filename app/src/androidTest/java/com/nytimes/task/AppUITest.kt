package com.nytimes.task

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AppUITest {

    @get:Rule
    val main = ActivityTestRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("com.nytimes.task", appContext.packageName)
    }

    @Test
    fun launchMainActivity() {
        onView(withId(R.id.controllerContainer)).check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun isKeyboardOpened() {
        onView(withId(R.id.action_search)).perform(click())
        Assert.assertTrue(isKeyboardShown())
    }

    private fun isKeyboardShown(): Boolean {
        val inputMethodManager = InstrumentationRegistry.getInstrumentation().targetContext.getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        return inputMethodManager.isAcceptingText
    }

    @Test
    fun isSearchQueryChanging(){
        val count = main.activity.findViewById<RecyclerView>(R.id.recyclerView)?.adapter?.itemCount
        onView(withId(R.id.action_search)).perform(typeText("the"))

        val newCount = main.activity.findViewById<RecyclerView>(R.id.recyclerView)?.adapter?.itemCount
        Assert.assertNotEquals(count, newCount)
    }

    @Test
    fun pullToRefresh(){
        onView(withId(R.id.refreshLayout)).perform(swipeDown()).check(ViewAssertions.matches(isDisplayed()))
    }
}