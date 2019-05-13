package com.jhowcs.chucknorrisapp.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jhowcs.chucknorrisapp.BaseSchedulers
import com.jhowcs.chucknorrisapp.data.JokeApi
import com.jhowcs.chucknorrisapp.repository.remote.JokeRepository
import com.jhowcs.chucknorrisapp.repository.remote.JokeService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class JokeViewModelTest {

    private val jokeServiceMock = mockk<JokeService>()
    private val schedulerMock = mockk<BaseSchedulers>()
    private val viewModel = JokeViewModel(JokeRepository(jokeServiceMock), schedulerMock)

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        every { schedulerMock.io() } returns Schedulers.trampoline()
        every { schedulerMock.ui() } returns Schedulers.trampoline()
    }

    @Test
    fun whenCallFetchJokeShouldReturnAJokeFromApiService() {
        val joke = JokeApi(
            "z916IyqnRJiVT5wcpcD_fw",
            "https:\\/\\/api.chucknorris.io\\/jokes\\/z916IyqnRJiVT5wcpcD_fw",
            "The Bearded Lady at Ringling Bros."
        )
        every { jokeServiceMock.fetchRandomJoke() } returns Observable.just(joke)

        val liveData = viewModel.fetchJoke()

        assertEquals("z916IyqnRJiVT5wcpcD_fw", liveData.value?.id)
        assertEquals("The Bearded Lady at Ringling Bros.", liveData.value?.value)
        assertEquals("https:\\/\\/api.chucknorris.io\\/jokes\\/z916IyqnRJiVT5wcpcD_fw", liveData.value?.url)
    }

    @Test
    fun whenCallFetchJokeFewTimesShouldReturnRandomOnEachCall() {
        val joke1 = JokeApi("id_1", "http://url_1", "Value 1")
        val joke2 = JokeApi("id_2", "http://url_2", "Value 2")
        val joke3 = JokeApi("id_3", "http://url_3", "Value 3")

        every { jokeServiceMock.fetchRandomJoke() } returnsMany
                listOf(
                    Observable.just(joke1),
                    Observable.just(joke2),
                    Observable.just(joke3)
                )

        val liveData = viewModel.fetchJoke()
        assertEquals(joke1, liveData.value)

        viewModel.fetchJoke()
        assertEquals(joke2, liveData.value)

        viewModel.fetchJoke()
        assertEquals(joke3, liveData.value)

        verify(exactly = 3) {jokeServiceMock.fetchRandomJoke() }
    }
}