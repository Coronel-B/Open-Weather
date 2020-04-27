package org.techdev.openweather.list.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager

import org.techdev.openweather.databinding.FragmentListForecastBinding
import org.techdev.openweather.list.vm.ForecastListVM

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

        viewModel.progressVisiblity.observe(this, Observer { visible ->
            binding.listProgressBar.visibility = if (visible) VISIBLE else GONE
        })

        binding.listRecyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getForecast()

        viewModel.forecastResult.observe(this, Observer {

            Log.d("TEST", it.toString())

    //            TODO: Set Adapter
    //            binding.listRecyclerView.adapter = ForecastListAdapter()


        })


    }



}
