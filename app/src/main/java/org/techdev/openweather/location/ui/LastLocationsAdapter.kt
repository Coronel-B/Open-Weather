package org.techdev.openweather.location.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.techdev.openweather.location.domain.LastLocation

class LastLocationsAdapter :
        ListAdapter<LastLocation, LastLocationsAdapter.LastLocationViewHolder>(LastLocationCallback()) {

        class LastLocationViewHolder(lastLocationView: View) : RecyclerView.ViewHolder(lastLocationView) {

        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastLocationViewHolder {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onBindViewHolder(holder: LastLocationViewHolder, position: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

}

class LastLocationCallback : DiffUtil.ItemCallback<LastLocation>() {

        override fun areItemsTheSame(oldItem: LastLocation, newItem: LastLocation): Boolean {
                return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: LastLocation, newItem: LastLocation): Boolean {
                return oldItem == newItem
        }

}