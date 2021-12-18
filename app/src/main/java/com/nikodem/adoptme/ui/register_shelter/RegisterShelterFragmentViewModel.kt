package com.nikodem.adoptme.ui.register_shelter

import com.nikodem.adoptme.utils.BaseViewModel
import com.nikodem.adoptme.utils.ViewState

class RegisterShelterFragmentViewModel :
    BaseViewModel<RegisterShelterFragmentViewState>(initialState = RegisterShelterFragmentViewState()) {
}

data class RegisterShelterFragmentViewState(override val isLoading: Boolean = false) : ViewState