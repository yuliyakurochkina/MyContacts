package com.example.mycontacts

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matcher

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(AndroidJUnit4::class)

class ExampleInstrumentedTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private fun clickItemWithId(id: Int): ViewAction {
        // отвечает за выполнение взаимодействия с данным элементом View
        return object : ViewAction {

            // с каким типом View идёт работа
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            // возврат описания действия просмотра
            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            // perform() - выполнение действия с данным View
            // UiController - контроллер для взаимодействия с пользовательским интерфейсом
            override fun perform(uiController: UiController, view: View) {
                val v = view.findViewById<View>(id) as View
                v.performClick()
            }
        }
    }

    @Test
    fun checkSavingContact_isSuccess() {

        onView(withId(R.id.fabAddContact))
            .check(matches(isDisplayed()))
            .perform(click())

        val name = "TestName"
        val surname = "TestSurname"
        val number = "89999999999"

        onView(withId(R.id.etName))
            .perform(typeText(name))
            .check(matches(withText(name)))

        onView(withId(R.id.etSurname))
            .perform(typeText(surname))
            .check(matches(withText(surname)))

        onView(withId(R.id.etNumber))
            .perform(typeText(number))
            .check(matches(withText(number)))

        onView(withText(R.string.add))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(R.id.rvContacts))
            .check(matches(isDisplayed()))

    }

    @Test
    fun checkSavedContactIsVisible_isSuccess() {

        val name = "TestName TestSurname"

        onView(withId(R.id.rvContacts))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(
                        withText(
                            name
                        )
                    )
                )
            )
    }

    @Test
    fun checkUpdatingContact_isSuccess() {

        val name = "Updating"
        val surname = "Updating"
        val number = "Updating"

        val nameUpdating = "TestNameUpdating"
        val surnameUpdating = "TestSurnameUpdating"
        val numberUpdating = "89999999999Updating"

        onView(withId(R.id.rvContacts))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    clickItemWithId(R.id.ivEditContact)
                )
            )

        onView(withId(R.id.etName))
            .perform(typeText(name))
            .check(matches(withText(nameUpdating)))

        onView(withId(R.id.etSurname))
            .perform(typeText(surname))
            .check(matches(withText(surnameUpdating)))

        onView(withId(R.id.etNumber))
            .perform(typeText(number))
            .check(matches(withText(numberUpdating)))

    }

    @Test
    fun checkUpdatedContactDelete_isSuccess() {

        onView(withId(R.id.rvContacts))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    clickItemWithId(R.id.ivDeleteContact)
                )
            )
    }

}