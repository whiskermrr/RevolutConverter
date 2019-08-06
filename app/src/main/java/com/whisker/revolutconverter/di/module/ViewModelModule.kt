package com.whisker.revolutconverter.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.whisker.revolutconverter.di.ViewModelFactory
import com.whisker.revolutconverter.di.ViewModelKey
import com.whisker.revolutconverter.view.currency.CurrencyConverterViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory) : ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CurrencyConverterViewModel::class)
    internal abstract fun bindCurrencyConverterViewModel(currencyConverterViewModel: CurrencyConverterViewModel) : ViewModel
}