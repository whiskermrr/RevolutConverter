package com.whisker.revolutconverter.view.currency

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.whisker.revolutconverter.R
import com.whisker.revolutconverter.model.CurrencyViewState
import com.whisker.revolutconverter.view.adapter.CurrencyAdapter
import com.whisker.revolutconverter.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_currency_converter.*

class CurrencyConverterFragment : BaseFragment(), CurrencyAdapter.OnCurrencyClickListener {

    private lateinit var viewModel: CurrencyConverterViewModel
    private lateinit var currencyAdapter: CurrencyAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currencyAdapter = CurrencyAdapter(this)
        layoutManager = LinearLayoutManager(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_currency_converter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvCurrencies.layoutManager = layoutManager
        rvCurrencies.adapter = currencyAdapter

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrencyConverterViewModel::class.java)
        viewModel.getCurrencies().observe(viewLifecycleOwner, Observer { viewState ->
            if(swipeLayout.isRefreshing)
                swipeLayout.isRefreshing = false

            when(viewState) {
                is CurrencyViewState.Data -> {
                    currencyAdapter.setCurrencies(viewState.currencies)
                }
                is CurrencyViewState.Error -> {
                    AlertDialog.Builder(context)
                        .setTitle(getString(R.string.alert_dialog_problem_title))
                        .setMessage(viewState.message)
                        .setNeutralButton(getString(R.string.dialog_ok), null)
                        .show()
                }
            }
        })

        swipeLayout.setOnRefreshListener {
            viewModel.refreshCurrency()
        }
    }

    override fun onCurrencyClicked(currencyCode: String, position: Int) {
        currencyAdapter.changeBaseCurrency(position)
        requestFirstItemFocus()
        viewModel.refreshCurrency(currencyCode)
    }

    private fun requestFirstItemFocus() {
        rvCurrencies.scrollToPosition(0)
        if(layoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
            rvCurrencies.post {
                rvCurrencies.findViewHolderForAdapterPosition(0)?.itemView?.requestFocus()
            }
        } else {
            rvCurrencies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if(dx == 0 && dy == 0) {
                        recyclerView.post {
                            recyclerView.findViewHolderForAdapterPosition(0)?.itemView?.requestFocus()
                            recyclerView.removeOnScrollListener(this)
                        }
                    }
                }
            })
        }
    }
}