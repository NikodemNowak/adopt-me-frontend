package com.nikodem.adoptme.ui.end_logging_in

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.nikodem.adoptme.R
import com.nikodem.adoptme.databinding.FragmentEndLogingInBinding
import com.nikodem.adoptme.utils.BaseFragment

class EndLoggingInFragment :
    BaseFragment<EndLoggingInFragmentViewState, EndLoggingInFragmentViewModel, FragmentEndLogingInBinding>(
        R.layout.fragment_end_loging_in, EndLoggingInFragmentViewModel::class
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loggingButton.setOnClickListener {
            findNavController().navigate(EndLoggingInFragmentDirections.actionEndLoggingInFragmentToFormFragment())
        }
    }
}