package com.jhowcs.chucknorrisapp.di

import com.jhowcs.chucknorrisapp.BaseSchedulers
import com.jhowcs.chucknorrisapp.SchedulersImpl
import com.jhowcs.chucknorrisapp.presentation.JokeViewModel
import com.jhowcs.chucknorrisapp.repository.remote.Api
import com.jhowcs.chucknorrisapp.repository.remote.BaseApi
import com.jhowcs.chucknorrisapp.repository.remote.JokeRepository
import com.jhowcs.chucknorrisapp.repository.remote.JokeService
import org.koin.dsl.module

val appModule = module {

    single<BaseApi> { Api() }
    single<BaseSchedulers> { SchedulersImpl() }

    single { (get<BaseApi>() as Api).retrofit.create(JokeService::class.java) }
    factory { JokeRepository(get()) }

    single { JokeViewModel(get(), get())  }
}