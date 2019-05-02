package com.jhowcs.chucknorrisapp.di

import com.jhowcs.chucknorrisapp.repository.remote.Api
import com.jhowcs.chucknorrisapp.repository.remote.BaseApi
import org.koin.dsl.module

val appModule = module {

    single<BaseApi> { Api() }
}