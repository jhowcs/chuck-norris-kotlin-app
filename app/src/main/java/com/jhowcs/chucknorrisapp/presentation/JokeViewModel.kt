package com.jhowcs.chucknorrisapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jhowcs.chucknorrisapp.BaseSchedulers
import com.jhowcs.chucknorrisapp.data.JokeApi
import com.jhowcs.chucknorrisapp.repository.remote.JokeRepository
import io.reactivex.disposables.CompositeDisposable

class JokeViewModel(private val repository: JokeRepository,
                    private val scheduler: BaseSchedulers) : ViewModel() {

    private val liveDataJoke = MutableLiveData<JokeApi>()

    private val compositeDisposable = CompositeDisposable()

    fun fetchJoke(): LiveData<JokeApi> {
        compositeDisposable.add(repository.fetchRandomJoke()
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .subscribe(
                { joke -> liveDataJoke.value = joke },
                {}
            ))

        return liveDataJoke
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}