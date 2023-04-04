package com.example.stocksapp

import androidx.navigation.findNavController
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.After
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class StocksActivityTest {

    @get:Rule
    val rule = ActivityScenarioRule(StocksActivity::class.java)

    @After
    fun cleanup() {
        rule.scenario.close()
    }

    @Test
    fun testFragmentLoaded() {
        rule.scenario.onActivity {
            val fragment = it.findNavController(R.id.nav_host_fragment_content_main)
            Assert.assertNotNull(fragment)
        }
    }
}
