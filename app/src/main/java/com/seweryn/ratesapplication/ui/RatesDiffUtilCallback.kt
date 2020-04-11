package com.seweryn.ratesapplication.ui

import androidx.recyclerview.widget.DiffUtil
import com.seweryn.ratesapplication.viewmodel.rates.RatesViewModel.RateWrapper

class RatesDiffUtilCallback(val newList: List<RateWrapper>, val oldList: List<RateWrapper>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newList[newItemPosition].currencyRate.currencyCode == oldList[oldItemPosition].currencyRate.currencyCode
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newItem = newList[newItemPosition]
        val oldItem = oldList[oldItemPosition]
        return newItem.currencyIconResId == oldItem.currencyIconResId
                && newItem.currencyName == oldItem.currencyName
                && newItem.currentDisplayRate == oldItem.currentDisplayRate
    }

}