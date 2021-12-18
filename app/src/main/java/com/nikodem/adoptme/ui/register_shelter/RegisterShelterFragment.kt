package com.nikodem.adoptme.ui.register_shelter

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.nikodem.adoptme.R
import com.nikodem.adoptme.databinding.FragmentRegisterShelterBinding
import com.nikodem.adoptme.ui.confirm_otp_code.AuthProcess
import com.nikodem.adoptme.utils.BaseFragment

class RegisterShelterFragment :
    BaseFragment<RegisterShelterFragmentViewState, RegisterShelterFragmentViewModel, FragmentRegisterShelterBinding>(
        R.layout.fragment_register_shelter, RegisterShelterFragmentViewModel::class
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.registerShelterButton.setOnClickListener {
            findNavController().navigate(RegisterShelterFragmentDirections.actionRegisterShelterFragmentToConfirmOtpCodeFragment(authProcess = AuthProcess.REGISTER))
        }
    }
}