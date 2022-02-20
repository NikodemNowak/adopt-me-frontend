package com.nikodem.adoptme.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.nikodem.adoptme.utils.AdoptMeError
import com.nikodem.adoptme.utils.ErrorEvent

class ErrorDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { fa ->
            (arguments?.get(ERROR_EVENT_KEY) as ErrorEvent?)?.let { errorEvent ->
                val message = when (errorEvent.throwable) {
                    is AdoptMeError.OtherError -> "Oops, Something went wrong."
                    is AdoptMeError.NetworkError -> "Something is not good with your Internet connection"
                    else -> "Unknown error"
                }

                val builder = AlertDialog.Builder(fa)
                builder.setMessage(message)
                    .setPositiveButton("Retry") { dialog, id ->
                        errorEvent.retryAction.invoke()
                        dialog.cancel()
                    }
                    .setNegativeButton("Cancel") { dialog, id ->
                        dialog.cancel()
                    }
                // Create the AlertDialog object and return it
                builder.create()
            }

        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {
        fun create(errorEvent: ErrorEvent): ErrorDialog {
            return ErrorDialog().apply {
                arguments = bundleOf(
                    ERROR_EVENT_KEY to errorEvent
                )
            }
        }

        const val ERROR_DIALOG_TAG = "ERROR_DIALOG_TAG"
        private const val ERROR_EVENT_KEY = "ERROR_EVENT_KEY"
    }
}