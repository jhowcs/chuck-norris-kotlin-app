package com.jhowcs.chucknorrisapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.jhowcs.chucknorrisapp.R
import com.jhowcs.chucknorrisapp.data.JokeApi
import com.jhowcs.chucknorrisapp.presentation.JokeViewModel
import kotlinx.android.synthetic.main.fragment_home_random_joke.*
import org.koin.android.ext.android.inject

class RandomJokeFragment: Fragment() {

    private val viewModel: JokeViewModel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_random_joke, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getJoke().takeIf { it.isNotEmpty() }?.apply {
            showJoke(this)
        } ?: fetchRandomJoke()

        btnGetJoke.setOnClickListener {
            fetchRandomJoke()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun fetchRandomJoke() {
        viewModel.fetchJoke().observe(this, Observer<JokeApi> {
            showJoke(it.value)
        })
    }

    private fun showJoke(joke: String) {
        random_joke.text = joke
    }
}