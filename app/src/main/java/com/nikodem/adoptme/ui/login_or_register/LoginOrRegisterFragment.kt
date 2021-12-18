package com.nikodem.adoptme.ui.login_or_register

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.nikodem.adoptme.R
import com.nikodem.adoptme.databinding.FragmentLoginOrRegisterBinding
import com.nikodem.adoptme.utils.BaseFragment

class LoginOrRegisterFragment :
    BaseFragment<LoginOrRegisterFragmentViewState, LoginOrRegisterFragmentViewModel, FragmentLoginOrRegisterBinding>(
        R.layout.fragment_login_or_register, LoginOrRegisterFragmentViewModel::class
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            loginButton.setOnClickListener {
                findNavController().navigate(LoginOrRegisterFragmentDirections.actionLoginOrRegisterFragmentToLoginFragment())
            }
            registerButton.setOnClickListener {
                findNavController().navigate(LoginOrRegisterFragmentDirections.actionLoginOrRegisterFragmentToRegisterUserFragment())
            }
            shelterRegisterButton.setOnClickListener {
                findNavController().navigate(LoginOrRegisterFragmentDirections.actionLoginOrRegisterFragmentToRegisterShelterFragment())
            }
        }
    }
}