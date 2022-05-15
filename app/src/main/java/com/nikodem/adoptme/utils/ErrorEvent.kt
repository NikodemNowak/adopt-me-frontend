package com.nikodem.adoptme.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ErrorEvent(
    val throwable: Throwable,
    val retryAction: () -> Unit,
    val onDialogCancelled: () ->  Unit
) : Parcelable
