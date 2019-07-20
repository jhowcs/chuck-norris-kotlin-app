package com.jhowcs.chucknorrisapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jhowcs.chucknorrisapp.BaseSchedulers
import com.jhowcs.chucknorrisapp.data.JokeApi
import com.jhowcs.chucknorrisapp.repository.remote.JokeRepository
import io.reactivex.disposables.CompositeDisposable

class JokeViewModel(
    private val repository: JokeRepository,
    private val scheduler: BaseSchedulers
) : ViewModel() {

    private val liveDataJoke = MutableLiveData<JokeApi>()

    private val _liveDataCategories = MutableLiveData<List<String>>()
    val liveDataCategories = _liveDataCategories

    private val compositeDisposable = CompositeDisposable()

    private var lastJoke = ""
    private var hasCategories = false

    fun getLastJoke() = lastJoke

    fun hasCategories() = hasCategories

    fun fetchJoke(): LiveData<JokeApi> {
        compositeDisposable.add(repository.fetchRandomJoke()
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .subscribe(
                { joke ->
                    lastJoke = joke.value
                    liveDataJoke.value = joke
                },
                {}
            ))

        return liveDataJoke
    }

    fun fetchCategories() {
        compositeDisposable.add(repository.fetchJokeCategories()
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .subscribe(
                {
                    hasCategories = true
                    _liveDataCategories.value = it
                },
                {})
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}