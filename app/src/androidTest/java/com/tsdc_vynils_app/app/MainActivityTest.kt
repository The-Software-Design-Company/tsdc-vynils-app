package com.tsdc_vynils_app.app


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers.allOf
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val viewGroup = onView(
            allOf(
                withId(R.id.container),
                withParent(
                    allOf(
                        withId(android.R.id.content),
                        withParent(withId(androidx.appcompat.R.id.decor_content_parent))
                    )
                ),
                isDisplayed()
            )
        )
        viewGroup.check(matches(isDisplayed()))


        val textView = onView(
            allOf(
                withId(R.id.allAlbumsTextView), withText("Todos los Álbumes"),
                withParent(
                    allOf(
                        withId(R.id.parentLinearLayout),
                        withParent(withId(R.id.contentAlbums))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Todos los Álbumes")))

        val linearLayout = onView(
            allOf(
                withId(R.id.parentContentLinearLayout),
                withParent(
                    allOf(
                        withId(R.id.parentLinearLayout),
                        withParent(withId(R.id.contentAlbums))
                    )
                ),
                isDisplayed()
            )
        )
        linearLayout.check(matches(isDisplayed()))
    }
}
