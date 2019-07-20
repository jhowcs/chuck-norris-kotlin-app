package com.jhowcs.chucknorrisapp.presentation.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jhowcs.chucknorrisapp.R
import com.jhowcs.chucknorrisapp.presentation.JokeViewModel
import kotlinx.android.synthetic.main.fragment_categories.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryFragment: Fragment() {

    private val jokeViewModel: JokeViewModel by viewModel()
    private val adapter = CategoryAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupList()

        jokeViewModel.hasCategories().takeIf { alreadyLoaded ->  !alreadyLoaded }.apply {
            jokeViewModel.fetchCategories()
        }

        jokeViewModel.liveDataCategories.observe(this, Observer<List<String>> {
            adapter.populateCategories(it)
        })
    }

    private fun setupList() {
        rvCategories.adapter = adapter
        rvCategories.layoutManager = LinearLayoutManager(this.context)
    }
}