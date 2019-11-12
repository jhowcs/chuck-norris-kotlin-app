package com.jhowcs.chucknorrisapp

import android.app.Application
import com.jhowcs.chucknorrisapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CustomApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CustomApp)
            androidLogger()
            modules(appModule)
        }
    }
}