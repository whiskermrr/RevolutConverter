package com.whisker.revolutconverter.view.currency

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.whisker.revolutconverter.model.CurrencyViewState
import com.whisker.revolutconverter.repository.CurrencyRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CurrencyConverterViewModel
@Inject constructor(
        private val currencyRepository: CurrencyRepository
) : ViewModel() {

    private val currencies = MutableLiveData<CurrencyViewState>()
    private lateinit var currencyDisposable: Disposable
    private var baseCurrency: String = "EUR"

    init {
        refreshCurrency("EUR")
    }

    fun refreshCurrency(currencyCode: String = baseCurrency) {
        if(baseCurrency != currencyCode) baseCurrency = currencyCode
        if(::currencyDisposable.isInitialized && !currencyDisposable.isDisposed) {
            currencyDisposable.dispose()
        }
        currencyDisposable = currencyRepository.getCurrencyConversion(baseCurrency)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ currencyMap ->
                    currencies.postValue(CurrencyViewState.Data(currencyMap))
                }, { error ->
                    currencies.postValue(CurrencyViewState.Error(error.message))
                })
    }

    fun getCurrencies() = currencies

    override fun onCleared() {
        super.onCleared()
        if(::currencyDisposable.isInitialized) {
            currencyDisposable.dispose()
        }
    }
}