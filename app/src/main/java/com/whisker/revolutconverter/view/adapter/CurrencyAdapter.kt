package com.whisker.revolutconverter.view.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.whisker.revolutconverter.R
import com.whisker.revolutconverter.model.Currency
import kotlinx.android.synthetic.main.currency_row.view.*
import org.jetbrains.anko.layoutInflater

class CurrencyAdapter : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    private var currencies: List<Currency> = mutableListOf()

    fun setCurrencies(newCurrencies: List<Currency>) {
        currencies = newCurrencies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = parent.context.layoutInflater.inflate(R.layout.currency_row, parent, false)
        return CurrencyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return currencies.size
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(currencies[position])
    }

    inner class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(currency: Currency) {
            val currencyFullName = java.util.Currency.getInstance(currency.code).displayName
            val str = "$currencyFullName ${currency.code} : ${currency.ratio}"
            itemView.tvCurrencyName.text = str
        }
    }
}