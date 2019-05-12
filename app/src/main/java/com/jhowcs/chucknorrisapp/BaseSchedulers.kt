package com.jhowcs.chucknorrisapp

import io.reactivex.Scheduler

interface BaseSchedulers {

    fun io(): Scheduler

    fun ui(): Scheduler
}