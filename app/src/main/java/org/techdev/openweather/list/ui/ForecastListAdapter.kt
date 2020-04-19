package org.techdev.openweather.list.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.techdev.openweather.domain.Forecast

private const val NEXT_FIVE_DAYS = 5;

class ForecastListAdapter(private val items: List<Forecast>) :
        RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

    fun setSubmitList(forecasts: List<Forecast>) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            View(
                parent.context
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        TODO:
//        holder.view
    }


    override fun getItemCount(): Int =
        NEXT_FIVE_DAYS

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
//        TODO:

    }




}