package com.nikodem.adoptme.ui.login_or_register

import com.nikodem.adoptme.utils.BaseViewModel
import com.nikodem.adoptme.utils.ViewState

class LoginOrRegisterFragmentViewModel :
    BaseViewModel<LoginOrRegisterFragmentViewState>(initialState = LoginOrRegisterFragmentViewState()) {
}

data class LoginOrRegisterFragmentViewState(override val isLoading: Boolean = false) : ViewState