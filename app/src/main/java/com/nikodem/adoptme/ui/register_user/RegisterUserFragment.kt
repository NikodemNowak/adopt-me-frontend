package com.nikodem.adoptme.ui.register_user

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.nikodem.adoptme.R
import com.nikodem.adoptme.databinding.FragmentRegisterUserBinding
import com.nikodem.adoptme.ui.confirm_otp_code.AuthProcess
import com.nikodem.adoptme.utils.BaseFragment

class RegisterUserFragment :
    BaseFragment<RegisterUserFragmentViewState, RegisterUserFragmentViewModel, FragmentRegisterUserBinding>(
        R.layout.fragment_register_user, RegisterUserFragmentViewModel::class
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.submitRegisterButton.setOnClickListener {
            findNavController().navigate(
                RegisterUserFragmentDirections.actionRegisterUserFragmentToConfirmOtpCodeFragment(
                    authProcess = AuthProcess.REGISTER
                )
            )
        }
    }
}