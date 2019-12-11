package com.mmg.guess

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MaterialActivityTest {
    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule<MaterialActivity>(MaterialActivity::class.java)

    @Test
    fun guessWrong() {
//        Espresso.onView(ViewMatchers.withId(R.id.ed_number)).perform(ViewActions.typeText("50"))
        val resources = activityTestRule.activity.resources
        val secretNumber = activityTestRule.activity.secretNumber
        for (n in 1..10) {
            if (n != secretNumber.secret) {
                onView(withId(R.id.ed_number)).perform(clearText())
                onView(withId(R.id.ed_number)).perform(typeText(n.toString()))
                onView(withId(R.id.btn_submit)).perform(click())
                val message =
                    if (n < secretNumber.secret) resources.getString(R.string.bigger)
                    else resources.getString(R.string.smaller)
                onView(withText(message)).check(matches(isDisplayed()))
                onView(withText(resources.getString(R.string.ok))).perform(click())
            }
        }
    }

    @Test
    fun replayWrong() {
        val resources = activityTestRule.activity.resources
        val secretNumber = activityTestRule.activity.secretNumber
        onView(withId(R.id.ed_number)).perform(clearText())
        onView(withId(R.id.ed_number)).perform(typeText("100"))
        onView(withId(R.id.btn_submit)).perform(click())
        onView(withText((R.string.ok))).perform(click())
        //剛輸入完鍵盤還在點不到fab
//        Espresso.pressBack()
        pressBack()
        onView(withId(R.id.fab)).perform(click())
        onView(withText(resources.getString(R.string.replay))).check(matches(isDisplayed()))
        onView(withText(resources.getString(R.string.are_you_sure))).check(matches(isDisplayed()))
        onView(withText(resources.getString(R.string.ok))).perform(click())
        onView(withId(R.id.counter)).check(matches(withText("0")))
        check(secretNumber.count == 0)
    }
}