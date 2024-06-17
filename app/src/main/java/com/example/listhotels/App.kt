package com.example.listhotels

import android.app.Application
import com.example.listhotels.di.dataModule
import com.example.listhotels.di.domainModule
import com.example.listhotels.di.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {


    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                dataModule, domainModule, viewModule
            )
        }
    }

}