package com.jhowcs.chucknorrisapp

import android.app.Application
import com.jhowcs.chucknorrisapp.di.appModule
import org.koin.core.context.startKoin

class CustomApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(appModule)
        }
    }
}