package com.example.stocksapp

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.stocksapp.common.hasItemCount
import com.example.stocksapp.common.viewAtPosition
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This class is for Android UI testing using Espresso.
 *
 * In coding challenge doc, it was advised not to do UI testing
 * so have included only basic ui test cases with positive scenarios.
 */

@RunWith(AndroidJUnit4::class)
@LargeTest
class StocksListFragmentTest {

    @get:Rule
    val rule = ActivityScenarioRule(StocksActivity::class.java)

    @Before
    fun setup() {
        Thread.sleep(1000) //Time to load UI.
    }

    @After
    fun cleanup() {
        rule.scenario.close()
    }

    @Test
    fun testProgressBar() {
        onView(withId(R.id.loading)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun testListVisible() {
        onView(withId(R.id.stocksList)).check(matches(isDisplayed()))
    }

    @Test
    fun testListItemsCount() {
        onView(withId(R.id.stocksList)).check(matches(hasItemCount(17)))
    }

    @Test
    fun testListFirstItem() {
        onView(withId(R.id.stocksList))
            .check(matches(viewAtPosition(0, hasDescendant(withText("TWTR")))))
    }

    @Test
    fun testListLastItem() {  //first scroll till last and then check item.
        onView(withId(R.id.stocksList))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(16))
            .check(matches(viewAtPosition(16, hasDescendant(withText("Rent The Runway")))))
    }

    @Test
    fun testListClick() {
        onView(withId(R.id.stocksList))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )
        //If on click something is shown/launch then we can assert that here.
    }
}
