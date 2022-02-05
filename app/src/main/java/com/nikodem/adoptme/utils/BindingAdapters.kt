package com.nikodem.adoptme.utils

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("onClick")
fun View.setOnClick(action: () -> Unit) {
    setOnClickListener { action.invoke() }
}

@BindingAdapter("visibleInvisible")
fun View.visibleInvisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
}