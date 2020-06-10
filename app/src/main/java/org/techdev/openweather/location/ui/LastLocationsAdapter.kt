package org.techdev.openweather.location.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.techdev.openweather.R
import org.techdev.openweather.location.domain.LastLocation

class LastLocationsAdapter :
        ListAdapter<LastLocation, LastLocationsAdapter.LastLocationViewHolder>(LastLocationCallback()) {

        class LastLocationViewHolder(lastLocationView: View) : RecyclerView.ViewHolder(lastLocationView) {

                private val name = lastLocationView.findViewById<TextView>(R.id.name)
                private val delete = lastLocationView.findViewById<Button>(R.id.delete)

                fun bind(lastLocation: LastLocation) {
                        name.text = lastLocation.name

//                        TODO: Delegar escucha del boton en el adapter.

                }

        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastLocationViewHolder =
                LastLocationViewHolder(
                        LayoutInflater.from(parent.context).inflate(
                                R.layout.list_item_last_location, parent, false
                        )
                )

        override fun onBindViewHolder(holder: LastLocationViewHolder, position: Int) {
                holder.bind(getItem(position))
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