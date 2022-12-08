package com.yape.recipes

import android.app.Application
import com.yape.recipes.di.AppDiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application: Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@Application)
            modules(AppDiModule.modules)
        }

    }

}