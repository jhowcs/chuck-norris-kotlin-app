package com.jhowcs.chucknorrisapp.repository.remote

import com.jhowcs.chucknorrisapp.data.JokeApi
import io.reactivex.Observable

class JokeRepository(private val jokeService: JokeService) {

    fun fetchRandomJoke(): Observable<JokeApi> {
        return jokeService.fetchRandomJoke()
    }

    fun fetchJokeCategories(): Observable<List<String>> {
        return jokeService.fetchCategories()
    }

    fun fetchJokeByCategorie(category: String) {
        jokeService.fetchJokeByCategory(category)

    }
}