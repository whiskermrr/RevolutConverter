package com.whisker.revolutconverter.view.currency

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.whisker.revolutconverter.model.Currency
import com.whisker.revolutconverter.repository.CurrencyRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CurrencyConverterViewModel
@Inject constructor(
        private val currencyRepository: CurrencyRepository
) : ViewModel() {

    private val currencies = MutableLiveData<List<Currency>>()
    private lateinit var currencyDisposable: Disposable

    init {
        setBaseCurrency("EUR")
    }

    fun setBaseCurrency(currencyCode: String) {
        if(::currencyDisposable.isInitialized && !currencyDisposable.isDisposed) {
            currencyDisposable.dispose()
        }
        currencyDisposable = currencyRepository.getCurrencyConversion(currencyCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ currencyMap ->
                    currencies.postValue(currencyMap)
                }, {

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