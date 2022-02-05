package com.nikodem.adoptme.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.nikodem.adoptme.R
import com.nikodem.adoptme.usecases.ClearDeviceUseCase

class CancelRegistrationDialog() : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Do you want to cancel registration?")
                .setPositiveButton("Yes") { dialog, id ->
                    findNavController().navigate(R.id.loginOrRegisterFragment)
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.cancel()
                }
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {
        const val TAG = "CancelRegistrationDialog"
    }
}