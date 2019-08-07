package com.whisker.revolutconverter.view.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.whisker.revolutconverter.R
import com.whisker.revolutconverter.model.Currency
import kotlinx.android.synthetic.main.currency_row.view.*
import org.jetbrains.anko.layoutInflater
import java.text.DecimalFormat

class CurrencyAdapter : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    private var currencies: MutableList<Currency> = mutableListOf()
    private var baseRate = 1f

    fun setCurrencies(newCurrencies: List<Currency>) {
        val diffResult =
            DiffUtil.calculateDiff(CurrencyDiffUtil(ArrayList(currencies), ArrayList(newCurrencies)))

        if(currencies.isNotEmpty() && newCurrencies.isNotEmpty()) {
            if(currencies[0].code == newCurrencies[0].code) {
                val baseCurrency = currencies[0]
                currencies.clear()
                if(newCurrencies.size > 1) {
                    currencies.addAll(newCurrencies.subList(1, newCurrencies.size))
                }
                currencies.add(0, baseCurrency)
            } else {
                currencies.clear()
                currencies.addAll(newCurrencies)
            }
        } else {
            currencies.clear()
            currencies.addAll(newCurrencies)
        }
        diffResult.dispatchUpdatesTo(this)
    }

    fun changeBaseRate(newRate: Float) {
        baseRate = newRate
        notifyItemRangeChanged(1, currencies.size - 1, CurrencyDiffUtil.UPDATE_RATE)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view =
            parent.context.layoutInflater.inflate(R.layout.currency_row, parent, false)
        return CurrencyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return currencies.size
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(currencies[position], position)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int, payloads: MutableList<Any>) {
        holder.bind(currencies[position], position, payloads)
    }

    inner class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textWatcher = object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.isNullOrEmpty()) {
                    changeBaseRate(0f)
                } else {
                    changeBaseRate(s.toString().toFloat())
                }
            }
        }

        fun bind(currency: Currency, position: Int, payloads: MutableList<Any>? = null) {
            itemView.apply {
                if(position != 0) {
                    etCurrencyRate.removeTextChangedListener(textWatcher)
                }
                if(!payloads.isNullOrEmpty()) {
                    when(payloads[0] as Int) {
                        CurrencyDiffUtil.UPDATE_RATE -> {
                            val rateText =
                                DecimalFormat("#.##").format(currency.rate * baseRate)
                            etCurrencyRate.setText(rateText)
                        }
                    }
                } else {
                    tvCurrencyCode.text = currency.code
                    tvCurrencyName.text = java.util.Currency.getInstance(currency.code).displayName
                    val rateText =
                        DecimalFormat("#.##").format(currency.rate * baseRate)
                    etCurrencyRate.setText(rateText)
                }
                if(position == 0) {
                    etCurrencyRate.addTextChangedListener(textWatcher)
                }
            }
        }
    }
}