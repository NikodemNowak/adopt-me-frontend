package com.nikodem.adoptme.ui.confirm_otp_code

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nikodem.adoptme.R
import com.nikodem.adoptme.databinding.FragmentConfirmOtpCodeBinding
import com.nikodem.adoptme.utils.BaseFragment

class ConfirmOtpCodeFragment :
    BaseFragment<ConfirmOtpCodeFragmentViewState, ConfirmOtpCodeFragmentViewModel, FragmentConfirmOtpCodeBinding>(
        R.layout.fragment_confirm_otp_code, ConfirmOtpCodeFragmentViewModel::class
    ) {
    private val args: ConfirmOtpCodeFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            if (args.authProcess == AuthProcess.REGISTER) {
                findNavController().navigate(ConfirmOtpCodeFragmentDirections.actionConfirmOtpCodeFragmentToEndRegistrationFragment())
            } else {
                findNavController().navigate(ConfirmOtpCodeFragmentDirections.actionConfirmOtpCodeFragmentToEndLoggingInFragment())

            }
        }
    }
}