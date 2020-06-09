package org.techdev.openweather.location.ui

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import org.techdev.openweather.R
import org.techdev.openweather.databinding.FragmentDialogLocationChangeBinding
import org.techdev.openweather.location.vm.ChangeLocationDialogVM
import java.lang.IllegalStateException

/**
 * PRO: Permite cambiar la ubicación por útlimas buscadas o desde el mapa
 * Source: https://developer.android.com/guide/topics/ui/dialogs
 */
class ChangeLocationDialogFragment: DialogFragment() {

    private lateinit var binding: FragmentDialogLocationChangeBinding

    private lateinit var changeLocationVM: ChangeLocationDialogVM

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        changeLocationVM = ViewModelProvider(this).get()

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            builder.setTitle("Change location")
            builder.setView(inflater.inflate(R.layout.fragment_dialog_location_change, null))
            builder.setMessage("Last searched")




            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


}