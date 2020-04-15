package com.seweryn.ratesapplication.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.seweryn.ratesapplication.R
import com.seweryn.ratesapplication.ui.views.DecimalDigitsInputFilter
import com.seweryn.ratesapplication.viewmodel.rates.RatesViewModel.RateWrapper
import kotlinx.android.synthetic.main.item_list_rates.view.*

class RatesRecyclerViewAdapter : RecyclerView.Adapter<RatesRecyclerViewAdapter.ViewHolder>() {

    private var rates: List<RateWrapper> = mutableListOf()

    fun updateRates(rates: List<RateWrapper>) {
        val diff = DiffUtil.calculateDiff(RatesDiffUtilCallback(rates, this.rates))
        this.rates = rates
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_rates, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = rates.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(rates[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(rateItem: RateWrapper) {
            itemView.apply {
                currency_code.text = rateItem.currencyRate.currencyCode
                currency_name.text = rateItem.currencyName
                currency_icon.setImageResource(rateItem.currencyIconResId)
                currency_input.setOnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) rateItem.selectAction.invoke(currency_input.text.toString())
                }

                currency_input.filters = arrayOf(DecimalDigitsInputFilter(2))
                currency_input.setOnTextChangedAction {
                    rateItem.amountEditAction.invoke(currency_input.text.toString())
                }

                currency_input.setText(rateItem.currentDisplayRate)
            }
        }
    }
}