package com.jhowcs.chucknorrisapp.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.jhowcs.chucknorrisapp.BaseSchedulers
import com.jhowcs.chucknorrisapp.R
import com.jhowcs.chucknorrisapp.data.JokeApi
import com.jhowcs.chucknorrisapp.repository.remote.JokeService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private val jokeService = mockk<JokeService>()
    private val scheduler = mockk<BaseSchedulers>()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        loadKoinModules( module(override = true) {
            single { scheduler }
            single { jokeService }
        })

        every { scheduler.io() } returns Schedulers.trampoline()
        every { scheduler.ui() } returns Schedulers.trampoline()
    }


    @Test
    fun whenStartsActivityShouldShowARandomJoke() {
        val joke = JokeApi(
            "z916IyqnRJiVT5wcpcD_fw",
            "https:\\/\\/api.chucknorris.io\\/jokes\\/z916IyqnRJiVT5wcpcD_fw",
            "The Bearded Lady at Ringling Bros."
        )

        every { jokeService.fetchRandomJoke() } returns Observable.just(joke)

        ActivityScenario.launch(MainActivity::class.java)

        onView(allOf(withId(R.id.random_joke), withText("The Bearded Lady at Ringling Bros.")))
            .check(matches(isDisplayed()))
    }

    @Test
    fun whenClickOnGetJoke_ShouldFetchANewJoke() {
        val joke1 = JokeApi("id_1", "http://url_1", "Value 1")
        val joke2 = JokeApi("id_2", "http://url_2", "Value 2")

        every { jokeService.fetchRandomJoke() } returnsMany
                listOf(Observable.just(joke1), Observable.just(joke2))

        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.btnGetJoke)).perform(click())

        onView(allOf(withId(R.id.random_joke), withText("Value 2")))
            .check(matches(isDisplayed()))

        verify(exactly = 2) { jokeService.fetchRandomJoke() }
    }

    @Test
    fun whenStartActivityShouldShowARandomJokeAndWhenRotateScreen_ShouldShowTheSameJoke() {
        val joke1 = JokeApi("id_1", "http://url_1", "Value 1")

        every { jokeService.fetchRandomJoke() } returns Observable.just(joke1)

        ActivityScenario.launch(MainActivity::class.java)

        onView(allOf(withId(R.id.random_joke), withText("Value 1")))
            .check(matches(isDisplayed()))

        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.setOrientationLeft()

        onView(allOf(withId(R.id.random_joke), withText("Value 1")))
            .check(matches(isDisplayed()))

        verify(exactly = 1) { jokeService.fetchRandomJoke() }
    }
}