package com.nikodem.adoptme.ui.end_registration

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.nikodem.adoptme.R
import com.nikodem.adoptme.databinding.FragmentEndRegistrationBinding
import com.nikodem.adoptme.ui.dialog.CancelRegistrationDialog
import com.nikodem.adoptme.utils.BaseFragment

class EndRegistrationFragment :
    BaseFragment<EndRegistrationFragmentViewState, EndRegistrationFragmentViewModel, FragmentEndRegistrationBinding>(
        R.layout.fragment_end_registration, EndRegistrationFragmentViewModel::class
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBackEvent = {

        }

        viewModel.navigateToFormFragmentEvent.observe(viewLifecycleOwner) {
            findNavController().navigate(
                EndRegistrationFragmentDirections.actionEndRegistrationFragmentToFormFragment()
            )
        }

        viewModel.navigateToHomeScreenFragmentEvent.observe(viewLifecycleOwner) {
            findNavController().navigate(
                EndRegistrationFragmentDirections.endRegistrationFragmentToHomeScreenFragment()
            )
        }

        binding.pinEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.onPinChange(text.toString())
        }

        binding.confirmPinEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.onConfirmPinChange(text.toString())
        }

        binding.registrationButton.setOnClickListener {
            viewModel.onRegisterButtonClick()
        }
    }
}