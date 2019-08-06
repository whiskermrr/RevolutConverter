package com.whisker.revolutconverter

import com.whisker.revolutconverter.di.applyAutoInjector
import com.whisker.revolutconverter.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        this.applyAutoInjector()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
                .application(this)
                .build()
    }
}