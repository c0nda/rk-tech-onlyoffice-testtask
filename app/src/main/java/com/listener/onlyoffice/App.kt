package com.listener.onlyoffice

import android.app.Application
import com.listener.onlyoffice.di.DaggerAppComponent

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    private fun initDI() {
        DI.appComponent = DaggerAppComponent
            .builder()
            .appContext(this)
            .build()
    }
}