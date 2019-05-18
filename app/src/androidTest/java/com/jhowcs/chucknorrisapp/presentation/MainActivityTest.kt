package com.jhowcs.chucknorrisapp.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jhowcs.chucknorrisapp.BaseSchedulers
import com.jhowcs.chucknorrisapp.R
import com.jhowcs.chucknorrisapp.data.JokeApi
import com.jhowcs.chucknorrisapp.di.appModuleTest
import com.jhowcs.chucknorrisapp.repository.remote.JokeService
import io.mockk.every
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

@RunWith(AndroidJUnit4::class)
class MainActivityTest: KoinTest {

    private val jokeService: JokeService by inject()
    private val scheduler: BaseSchedulers by inject()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        loadKoinModules(appModuleTest)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun whenStartsActivityShouldShowARandomJoke() {
        val joke = JokeApi(
            "z916IyqnRJiVT5wcpcD_fw",
            "https:\\/\\/api.chucknorris.io\\/jokes\\/z916IyqnRJiVT5wcpcD_fw",
            "The Bearded Lady at Ringling Bros."
        )

        every { jokeService.fetchRandomJoke() } returns Observable.just(joke)
        every { scheduler.io() } returns Schedulers.trampoline()
        every { scheduler.ui() } returns Schedulers.trampoline()

        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)

        onView(allOf(withId(R.id.random_joke), withText("The Bearded Lady at Ringling Bros.")))
            .check(matches(isDisplayed()))
    }
}