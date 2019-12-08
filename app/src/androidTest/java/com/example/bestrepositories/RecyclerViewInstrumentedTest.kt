package com.example.bestrepositories

import androidx.core.view.size
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.bestrepositories.RecyclerViewItemCountAssertion.Companion.withItemCount
import com.example.bestrepositories.feature.home.HomeActivity
import com.example.bestrepositories.feature.home.viewholder.RepositoryViewHolder
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
class RecyclerViewInstrumentedTest {
    private lateinit var stringToBetyped: String

    @get:Rule
    var activityRule: ActivityTestRule<HomeActivity>
            = ActivityTestRule(HomeActivity::class.java)


    @Test
    fun loading_repositories() {

        val recyclerview = activityRule.activity.findViewById<RecyclerView>(R.id.recyclerview_repositories)

        Thread.sleep(5000)

        // Type text and then press the button.
        onView(withId(R.id.recyclerview_repositories))
            .check(withItemCount(greaterThan(0)))

        val oldSize = recyclerview.adapter!!.itemCount

        onView(withId(R.id.recyclerview_repositories))
            .perform(RecyclerViewActions.scrollToPosition<RepositoryViewHolder>(recyclerview.adapter!!.itemCount -1))

        Thread.sleep(5000)

        assert(oldSize < recyclerview.size)


        onView(withId(R.id.recyclerview_repositories))
            .perform(RecyclerViewActions.scrollToPosition<RepositoryViewHolder>(recyclerview.adapter!!.itemCount -1))
    }

}
