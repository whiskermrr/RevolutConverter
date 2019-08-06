package com.whisker.revolutconverter.di.module

import com.whisker.revolutconverter.MainActivity
import com.whisker.revolutconverter.view.currency.CurrencyConverterFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    internal abstract fun bindMainActivity() : MainActivity

    @ContributesAndroidInjector
    internal abstract fun bindCurrencyConverterFragment() : CurrencyConverterFragment
}