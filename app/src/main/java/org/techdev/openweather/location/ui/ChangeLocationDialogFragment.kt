package org.techdev.openweather.location.ui

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.google.android.gms.maps.model.LatLng
import org.techdev.openweather.databinding.FragmentDialogLocationChangeBinding
import org.techdev.openweather.location.domain.Geolocation
import org.techdev.openweather.location.domain.LastLocation
import org.techdev.openweather.location.vm.ChangeLocationDialogVM
import java.lang.IllegalStateException

/**
 * PRO: Permite cambiar la ubicación por útlimas buscadas o desde el mapa
 * Source: https://developer.android.com/guide/topics/ui/dialogs
 */
class ChangeLocationDialogFragment(): DialogFragment() {

    private lateinit var binding: FragmentDialogLocationChangeBinding
    private lateinit var changeLocationVM: ChangeLocationDialogVM
    private lateinit var lastLocationsAdapter: LastLocationsAdapter

//    TODO: me esta quedando nulo
    private val sharedPreferences: SharedPreferences? =
        activity?.getSharedPreferences("prefs_open_weather", Context.MODE_PRIVATE)

    val test = this.activity?.getSharedPreferences("test", Context.MODE_PRIVATE)

    /**
     * OBS: Tambien se podria setear el adapter antes de devolver el dialogo
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentDialogLocationChangeBinding.inflate(LayoutInflater.from(context))

        return activity?.let {
            val builder = AlertDialog.Builder(it)

            builder.setTitle("Change location")
            builder.setView(binding.root)
            builder.setMessage("Last searched")

            builder.setPositiveButton("New Search") { dialogInterface: DialogInterface, i: Int ->
                Log.d("TAG", "")
            }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lastLocationsAdapter = LastLocationsAdapter()
        binding.listRecyclerView.adapter = lastLocationsAdapter

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        changeLocationVM = ViewModelProvider(this).get()

        setupObservers()
    }

    private fun setupObservers() {
        changeLocationVM.provideSharedPreferences(sharedPreferences)

        Log.d("TEST", test.toString())

        changeLocationVM.addLastLocationToList(
            LastLocation("CABA", Geolocation(LatLng(0.0,0.0)))
        )

        changeLocationVM.addLastLocationToList(
            LastLocation("CABA", Geolocation(LatLng(0.0,0.0)))
        )

        lastLocationsAdapter.setSubmitList(changeLocationVM.getLastLocations())
    }





}