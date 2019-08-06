package com.whisker.revolutconverter.di.component

import com.whisker.revolutconverter.App
import com.whisker.revolutconverter.MainActivity
import com.whisker.revolutconverter.di.module.AppModule
import com.whisker.revolutconverter.di.module.BuildersModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    BuildersModule::class])

interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent

    }

    override fun inject(app: App)

    fun inject(mainActivity: MainActivity)
}