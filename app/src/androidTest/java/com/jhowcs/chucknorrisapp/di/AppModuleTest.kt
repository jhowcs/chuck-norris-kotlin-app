package com.jhowcs.chucknorrisapp.di

import com.jhowcs.chucknorrisapp.BaseSchedulers
import com.jhowcs.chucknorrisapp.presentation.JokeViewModel
import com.jhowcs.chucknorrisapp.repository.remote.Api
import com.jhowcs.chucknorrisapp.repository.remote.BaseApi
import com.jhowcs.chucknorrisapp.repository.remote.JokeRepository
import com.jhowcs.chucknorrisapp.repository.remote.JokeService
import io.mockk.mockk
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModuleTest = module {

    single<BaseApi> { Api() }
    single { mockk<BaseSchedulers>() }

    single { mockk<JokeService>() }
    factory { JokeRepository(get()) }

    viewModel { JokeViewModel(get(), get())  }
}