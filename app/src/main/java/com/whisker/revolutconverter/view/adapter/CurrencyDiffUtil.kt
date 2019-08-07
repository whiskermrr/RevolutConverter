package com.whisker.revolutconverter.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.whisker.revolutconverter.model.Currency

class CurrencyDiffUtil(
    private val oldCurrencies: List<Currency>,
    private val newCurrencies: List<Currency>
) : DiffUtil.Callback() {

    companion object {
        const val UPDATE_RATE = 1
    }

    override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean {
        return oldCurrencies[oldPos].code == newCurrencies[newPos].code
    }

    override fun getOldListSize(): Int {
        return oldCurrencies.size
    }

    override fun getNewListSize(): Int {
        return newCurrencies.size
    }

    override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean {
        if(oldPos == 0 && newPos == 0) {
            return true
        }
        return (oldCurrencies[oldPos].rate == newCurrencies[newPos].rate)
    }

    override fun getChangePayload(oldPos: Int, newPos: Int): Any? {
        if(oldCurrencies[oldPos].rate != newCurrencies[newPos].rate) {
            return UPDATE_RATE
        }
        return null
    }
}