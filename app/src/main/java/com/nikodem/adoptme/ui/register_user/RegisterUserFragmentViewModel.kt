package com.nikodem.adoptme.ui.register_user

import com.nikodem.adoptme.utils.BaseViewModel
import com.nikodem.adoptme.utils.ViewState

class RegisterUserFragmentViewModel :
    BaseViewModel<RegisterUserFragmentViewState>(initialState = RegisterUserFragmentViewState()) {
}

data class RegisterUserFragmentViewState(override val isLoading: Boolean = false) : ViewState