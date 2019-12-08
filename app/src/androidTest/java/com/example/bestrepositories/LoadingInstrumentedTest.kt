package com.example.bestrepositories

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.bestrepositories.RecyclerViewItemCountAssertion.Companion.withItemCount
import com.example.bestrepositories.feature.home.HomeActivity
import org.hamcrest.Matchers.greaterThan

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class LoadingInstrumentedTest {
    private lateinit var stringToBetyped: String

    @get:Rule
    var activityRule: ActivityTestRule<HomeActivity>
            = ActivityTestRule(HomeActivity::class.java)


    @Test
    fun check_loading() {

        Thread.sleep(50)

        onView(withId(R.id.loading)).check(matches(isDisplayed()))
    }

}
