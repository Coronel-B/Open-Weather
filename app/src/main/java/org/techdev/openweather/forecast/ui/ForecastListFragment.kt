package org.techdev.openweather.forecast.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager

import org.techdev.openweather.databinding.FragmentListForecastBinding
import org.techdev.openweather.forecast.vm.ForecastListVM

/**
 * PRO: Render a list of daily forecasts for the next 5 days
 */
class ForecastListFragment : Fragment() {

    private lateinit var binding: FragmentListForecastBinding

    private lateinit var viewModel: ForecastListVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListForecastBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this).get()

        binding.listRecyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
/*
        setupObservers()
        getForecasts()*/
    }

/*
    private fun setupObservers() {
        viewModel.mutableScreenState.observe(this, Observer {
            binding.listProgressBar.visibility = if (it == ScreenState.LOADING) VISIBLE else GONE
        })

        viewModel.forecasts.observe(this, Observer {
            Log.d("TEST", it.toString())

            //            TODO: Set Adapter
//            binding.listRecyclerView.adapter = ForecastListAdapter()
        })
    }

    private fun getForecasts() {
        viewModel.getForecasts()
    }
*/

}
