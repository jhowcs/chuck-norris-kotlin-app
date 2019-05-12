package com.jhowcs.chucknorrisapp.repository.remote

import com.jhowcs.chucknorrisapp.data.JokeApi
import io.reactivex.Observable

class JokeRepository(api: BaseApi) {

    private val jokeService = (api as Api).retrofit.create(JokeService::class.java)

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