package com.nikodem.adoptme.ui.profile

import com.nikodem.adoptme.utils.BaseViewModel
import com.nikodem.adoptme.utils.ViewState

class ProfileFragmentViewModel :
    BaseViewModel<ProfileFragmentViewState>(initialState = ProfileFragmentViewState()) {
}

data class ProfileFragmentViewState(override val isLoading: Boolean = false) : ViewState