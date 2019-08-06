package com.whisker.revolutconverter.view.currency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.whisker.revolutconverter.R
import com.whisker.revolutconverter.view.adapter.CurrencyAdapter
import com.whisker.revolutconverter.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_currency_converter.*

class CurrencyConverterFragment : BaseFragment() {

    private lateinit var viewModel: CurrencyConverterViewModel
    private lateinit var currencyAdapter: CurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currencyAdapter = CurrencyAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_currency_converter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrencyConverterViewModel::class.java)

        rvCurrencies.layoutManager = LinearLayoutManager(context)
        rvCurrencies.adapter = currencyAdapter

        viewModel.getCurrencies().observe(this, Observer { currencies ->
            currencyAdapter.setCurrencies(currencies)
        })
    }
}