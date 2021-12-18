package com.nikodem.adoptme.ui.end_registration

import com.nikodem.adoptme.utils.BaseViewModel
import com.nikodem.adoptme.utils.ViewState

class EndRegistrationFragmentViewModel :
    BaseViewModel<EndRegistrationFragmentViewState>(initialState = EndRegistrationFragmentViewState())

data class EndRegistrationFragmentViewState(override val isLoading: Boolean = false) : ViewState