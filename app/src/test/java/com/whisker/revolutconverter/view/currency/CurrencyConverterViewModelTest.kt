package com.whisker.revolutconverter.view.currency

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.whisker.revolutconverter.RxImmediateSchedulerRule
import com.whisker.revolutconverter.api.exception.InternalServerErrorException
import com.whisker.revolutconverter.api.exception.NoConnectivityException
import com.whisker.revolutconverter.model.Currency
import com.whisker.revolutconverter.model.CurrencyViewState
import com.whisker.revolutconverter.repository.CurrencyRepository
import io.reactivex.Flowable
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CurrencyConverterViewModelTest {

    @Rule
    @JvmField
    var rule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    val ruleForLivaData = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockCurrencyRepository: CurrencyRepository

    @Mock
    private lateinit var mockObserver: Observer<CurrencyViewState>

    private val currencies = listOf(
        Currency("EUR", 1f),
        Currency("PLN", 4.3f),
        Currency("RUB", 79.4f),
        Currency("ISK", 127.32f)
    )

    private val updatedCurrencies = listOf(
        Currency("EUR", 1f),
        Currency("PLN", 4.31f),
        Currency("RUB", 79.2f),
        Currency("ISK", 126.98f)
    )

    @Before
    fun setUp() {
    }

    @Test
    fun getCurrenciesSuccess() {
        whenever(mockCurrencyRepository.getCurrencyConversion("EUR"))
            .thenReturn(Flowable.just(currencies))

        val viewModel = CurrencyConverterViewModel(mockCurrencyRepository)
        viewModel.getCurrencies().observeForever(mockObserver)

        assert(viewModel.getCurrencies().value is CurrencyViewState.Data)
        assertEquals((viewModel.getCurrencies().value as CurrencyViewState.Data).currencies, currencies)

        whenever(mockCurrencyRepository.getCurrencyConversion("EUR"))
            .thenReturn(Flowable.just(updatedCurrencies))

        viewModel.refreshCurrency("EUR")
        assert(viewModel.getCurrencies().value is CurrencyViewState.Data)
        assertEquals((viewModel.getCurrencies().value as CurrencyViewState.Data).currencies, updatedCurrencies)
    }

    @Test
    fun getCurrenciesSuccess2() {
        val result = Flowable.merge(
            Flowable.just(currencies),
            Flowable.just(updatedCurrencies)
        )
        whenever(mockCurrencyRepository.getCurrencyConversion("EUR"))
            .thenReturn(result)

        val viewModel = CurrencyConverterViewModel(mockCurrencyRepository)
        viewModel.getCurrencies().observeForever(mockObserver)

        assert(viewModel.getCurrencies().value is CurrencyViewState.Data)
        assertEquals((viewModel.getCurrencies().value as CurrencyViewState.Data).currencies, updatedCurrencies)
        verify(mockObserver, times(1)).onChanged(any())
    }

    @Test
    fun getCurrenciesFailed() {
        val error = NoConnectivityException()
        val result = Flowable.error<List<Currency>>(error)

        whenever(mockCurrencyRepository.getCurrencyConversion("EUR"))
            .thenReturn(result)

        val viewModel = CurrencyConverterViewModel(mockCurrencyRepository)
        viewModel.getCurrencies().observeForever(mockObserver)

        assert(viewModel.getCurrencies().value is CurrencyViewState.Error)
        assertEquals((viewModel.getCurrencies().value as CurrencyViewState.Error).message, error.message)
        verify(mockObserver, times(1)).onChanged(any())
    }

    @Test
    fun getCurrenciesSucceedThenFailed() {
        val error = InternalServerErrorException()
        val result = Flowable.merge(
            Flowable.just(currencies),
            Flowable.error<List<Currency>>(error)
        )

        whenever(mockCurrencyRepository.getCurrencyConversion("EUR"))
            .thenReturn(result)

        val viewModel = CurrencyConverterViewModel(mockCurrencyRepository)
        viewModel.getCurrencies().observeForever(mockObserver)

        assert(viewModel.getCurrencies().value is CurrencyViewState.Error)
        assertEquals((viewModel.getCurrencies().value as CurrencyViewState.Error).message, error.message)
        verify(mockObserver, times(1)).onChanged(any())
    }

    @Test
    fun getCurrenciesFailedThenSucceed() {
        val error = InternalServerErrorException()
        val result = Flowable.error<List<Currency>>(error)
            .onErrorResumeNext(Flowable.just(currencies))

        whenever(mockCurrencyRepository.getCurrencyConversion("EUR"))
            .thenReturn(result)

        val viewModel = CurrencyConverterViewModel(mockCurrencyRepository)
        viewModel.getCurrencies().observeForever(mockObserver)

        assert(viewModel.getCurrencies().value is CurrencyViewState.Data)
        assertEquals((viewModel.getCurrencies().value as CurrencyViewState.Data).currencies, currencies)
        verify(mockObserver, times(1)).onChanged(any())
    }

    @After
    fun tearDown() {
    }
}