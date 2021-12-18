package com.nikodem.adoptme.ui.end_logging_in

import com.nikodem.adoptme.utils.BaseViewModel
import com.nikodem.adoptme.utils.ViewState

class EndLoggingInFragmentViewModel :
    BaseViewModel<EndLoggingInFragmentViewState>(initialState = EndLoggingInFragmentViewState()) {
}

data class EndLoggingInFragmentViewState(override val isLoading: Boolean = false) : ViewState