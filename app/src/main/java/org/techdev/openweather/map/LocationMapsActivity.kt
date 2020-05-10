package org.techdev.openweather.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.maps.GoogleMap
import org.techdev.openweather.R

class LocationMapsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_maps)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_locality_picker_map)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        val actionPick: Boolean = isActionPick()
        title = if (actionPick) getString(R.string.select_locality) else getString(R.string.text_field_locality)

        var view = supportFragmentManager
            .findFragmentById(R.id.fragment_origin_locality_picker_map_container) as LocationPickerMapFragment?

        if (view == null) {
            view = LocationPickerMapFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_origin_locality_picker_map_container, view)
                .commit()
        }

    }

    private fun isActionPick(): Boolean {
        return ACTION_PICK_LOCATION == intent.action
    }

    companion object {
        val ACTION_PICK_LOCATION = "org.techdev.openweather.action.ACTION_PICK_LOCATION"

        //    Representa la ciudad que ya se ha seleccionado
        val EXTRA_LOCATION = "org.techdev.openweather.EXTRA_LOCATION"
    }

}
