package com.kardusinfo.footballapps

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.RecyclerView
import com.kardusinfo.footballapps.R.id.*
import com.kardusinfo.footballapps.activity.ClubHomeActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ClubTest {
    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(ClubHomeActivity::class.java)

    @Before
    fun SetUp() {
        Espresso.registerIdlingResources(IdlingResourceProvider.idlingRes)
    }

    @Test
    fun AddtoFav() {
        onView(withId(rvTeams)).check(matches(isDisplayed()))
//        Thread.sleep(2000)
        onView(withId(rvTeams)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(19))
        onView(withId(rvTeams)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(19, click()))
//        Thread.sleep(5000)

//        onView(withId(action_favourite)).check(matches(isDisplayed()))
//        onView(withId(action_favourite)).perform(click())
        pressBack()

        onView(withId(rvTeams)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(4))
        onView(withId(rvTeams)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(4, click()))

//        onView(withId(action_favourite)).check(matches(isDisplayed()))
//        onView(withId(action_favourite)).perform(click())
//        pressBack()

//        onView(withId(act_favorites)).check(matches(isDisplayed()))
//        onView(withId(act_favorites)).perform(click())

//        onView(withId(recyclerView)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
//        onView(withId(recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

//        onView(withId(action_favourite)).check(matches(isDisplayed()))
//        onView(withId(action_favourite)).perform(click())
//        pressBack()
//
//        onView(withId(act_favorites)).perform(click())
        Thread.sleep(1000)

    }

}