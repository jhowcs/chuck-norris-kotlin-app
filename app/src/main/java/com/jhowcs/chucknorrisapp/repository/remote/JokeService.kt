package com.jhowcs.chucknorrisapp.repository.remote

import com.jhowcs.chucknorrisapp.data.JokeApi
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface JokeService {

    @GET("random")
    fun fetchRandomJoke(): Observable<JokeApi>

    @GET("random")
    fun fetchJokeByCategory(@Query("category") category: String): Observable<JokeApi>

    @GET("categories")
    fun fetchCategories(): Observable<List<String>>
}