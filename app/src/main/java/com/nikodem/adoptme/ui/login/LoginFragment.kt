package com.nikodem.adoptme.ui.login

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.nikodem.adoptme.R
import com.nikodem.adoptme.databinding.FragmentLoginBinding
import com.nikodem.adoptme.ui.confirm_otp_code.AuthProcess
import com.nikodem.adoptme.utils.BaseFragment

class LoginFragment :
    BaseFragment<LoginFragmentViewState, LoginFragmentViewModel, FragmentLoginBinding>(
        R.layout.fragment_login, LoginFragmentViewModel::class
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.phoneNumberEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.onPhoneNumberChange(text.toString())
        }

        viewModel.navigateToVerifyOtpCodeFragment.observe(viewLifecycleOwner) {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToConfirmOtpCodeFragment(
                    authProcess = AuthProcess.LOGIN
                )
            )
        }
    }
}