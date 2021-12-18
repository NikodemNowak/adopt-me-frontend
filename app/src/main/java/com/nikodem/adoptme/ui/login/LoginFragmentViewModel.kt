package com.nikodem.adoptme.ui.login

import com.nikodem.adoptme.utils.BaseViewModel
import com.nikodem.adoptme.utils.ViewState

class LoginFragmentViewModel :
    BaseViewModel<LoginFragmentViewState>(initialState = LoginFragmentViewState()) {
}

data class LoginFragmentViewState(override val isLoading: Boolean = false) : ViewState