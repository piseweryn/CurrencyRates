package com.seweryn.ratesapplication.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.seweryn.ratesapplication.R
import com.seweryn.ratesapplication.ui.views.extensions.showConditionally
import com.seweryn.ratesapplication.viewmodel.rates.RatesViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: RatesViewModel

    private val layoutManager = LinearLayoutManager(this)
    private val adapter = RatesRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidInjection.inject(this)
        setSupportActionBar(toolbar)
        rates_recycler_view.layoutManager = layoutManager
        rates_recycler_view.adapter = adapter
        rates_recycler_view.itemAnimator?.changeDuration = 0
        viewModel = ViewModelProvider(this, viewModelFactory).get(RatesViewModel::class.java)
        viewModel.rates.observe(this, Observer {
            adapter.updateRates(it)
        })
        viewModel.error.observe(this, Observer { error ->
            error?.let { error_retry_button.setOnClickListener { error.retryAction.invoke() } }
            error_container.showConditionally(error != null)
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.start()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stop()
    }
}
