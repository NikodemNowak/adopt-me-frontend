package com.nikodem.adoptme.ui.end_registration

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.nikodem.adoptme.R
import com.nikodem.adoptme.databinding.FragmentEndRegistrationBinding
import com.nikodem.adoptme.utils.BaseFragment

class EndRegistrationFragment :
    BaseFragment<EndRegistrationFragmentViewState, EndRegistrationFragmentViewModel, FragmentEndRegistrationBinding>(
        R.layout.fragment_end_registration, EndRegistrationFragmentViewModel::class
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.registrationButton.setOnClickListener {
            findNavController().navigate(EndRegistrationFragmentDirections.actionEndRegistrationFragmentToFormFragment())
        }
    }
}