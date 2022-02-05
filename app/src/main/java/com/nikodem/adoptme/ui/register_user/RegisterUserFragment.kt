package com.nikodem.adoptme.ui.register_user

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.DialogFragmentNavigator
import androidx.navigation.fragment.findNavController
import com.nikodem.adoptme.R
import com.nikodem.adoptme.databinding.FragmentRegisterUserBinding
import com.nikodem.adoptme.repositories.CacheManager
import com.nikodem.adoptme.repositories.CacheManagerImpl
import com.nikodem.adoptme.ui.confirm_otp_code.AuthProcess
import com.nikodem.adoptme.ui.dialog.CancelRegistrationDialog
import com.nikodem.adoptme.usecases.ClearDeviceUseCase
import com.nikodem.adoptme.usecases.ClearDeviceUseCaseImpl
import com.nikodem.adoptme.utils.BaseFragment
import timber.log.Timber

class RegisterUserFragment :
    BaseFragment<RegisterUserFragmentViewState, RegisterUserFragmentViewModel, FragmentRegisterUserBinding>(
        R.layout.fragment_register_user, RegisterUserFragmentViewModel::class
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fun showDialog() {
            val newFragment = CancelRegistrationDialog()
            newFragment.show(childFragmentManager, "CancelRegistrationDialog")
        }

        onBackEvent = {
            showDialog()
        }

        binding.firstNameEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.onFirstNameChange(text.toString())
        }

        binding.lastNameEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.onLastNameChange(text.toString())
        }

        binding.cityEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.onCityChange(text.toString())
        }

        binding.phoneNumberEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.onPhoneNumberChange(text.toString())
        }

        binding.emailEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.onEmailChange(text.toString())
        }
        viewModel.navigateToOtpCodeFragmentEvent.observe(viewLifecycleOwner) {
            findNavController().navigate(
                RegisterUserFragmentDirections.actionRegisterUserFragmentToConfirmOtpCodeFragment(
                    authProcess = AuthProcess.REGISTER
                )
            )
        }
    }
}