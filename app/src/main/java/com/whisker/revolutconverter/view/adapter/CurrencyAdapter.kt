package com.whisker.revolutconverter.view.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.whisker.revolutconverter.R
import com.whisker.revolutconverter.model.Currency
import com.whisker.revolutconverter.utils.loadFlag
import kotlinx.android.synthetic.main.currency_row.view.*
import org.jetbrains.anko.layoutInflater
import java.text.DecimalFormat

class CurrencyAdapter(
    private val currencyClickListener: OnCurrencyClickListener
) : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    private var currencies: MutableList<Currency> = mutableListOf()
    private var baseRate = 1f

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

    fun setCurrencies(list: List<Currency>) {
        val newCurrencies =
            list.sortedBy { currency ->  currencies.map { it.code }.indexOf(currency.code) }

        val diffResult =
            DiffUtil.calculateDiff(CurrencyDiffUtil(ArrayList(currencies), ArrayList(newCurrencies)))


        if(currencies.isNotEmpty() && newCurrencies.isNotEmpty()
            && currencies[0].code == newCurrencies[0].code) {
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
        diffResult.dispatchUpdatesTo(this)
    }

    fun changeBaseCurrency(position: Int) {
        val newBaseCurrency= currencies.removeAt(position)
        baseRate *= newBaseCurrency.rate
        newBaseCurrency.rate = 1f
        currencies.add(0, newBaseCurrency)
        notifyItemMoved(position, 0)
        notifyItemChanged(0)
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

        fun bind(currency: Currency, position: Int, payloads: MutableList<Any>? = null) {
            itemView.apply {
                if(position != 0) {
                    etCurrencyRate.removeTextChangedListener(textWatcher)
                    setOnClickListener {
                        currencyClickListener.onCurrencyClicked(currency.code, position)
                    }
                }
                if(!payloads.isNullOrEmpty()) {
                    when(payloads[0] as Int) {
                        CurrencyDiffUtil.UPDATE_RATE -> {
                            setCurrencyRate(currency.rate)
                        }
                    }
                } else {
                    setFlagImage(currency.code)
                    setCurrencyCode(currency.code)
                    setCurrencyName(currency.code)
                    setCurrencyRate(currency.rate)
                }
                if(position == 0) {
                    etCurrencyRate.addTextChangedListener(textWatcher)
                    setOnClickListener(null)
                }
            }
        }

        private fun setFlagImage(code: String) {
            itemView.ivCurrency.loadFlag(code)
        }

        private fun setCurrencyCode(code: String) {
            itemView.tvCurrencyCode.text = code
        }

        private fun setCurrencyName(code: String) {
            itemView.tvCurrencyName.text = java.util.Currency.getInstance(code).displayName
        }

        private fun setCurrencyRate(rate: Float) {
            val rateText =
                DecimalFormat("#.##").format(rate * baseRate)
            itemView.etCurrencyRate.setText(rateText)
        }
    }

    interface OnCurrencyClickListener {
        fun onCurrencyClicked(currencyCode: String, position: Int)
    }
}