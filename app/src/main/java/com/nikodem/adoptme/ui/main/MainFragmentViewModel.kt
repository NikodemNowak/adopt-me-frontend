package com.nikodem.adoptme.ui.main

import com.nikodem.adoptme.utils.BaseViewModel
import com.nikodem.adoptme.utils.ViewState

class MainFragmentViewModel :
    BaseViewModel<MainFragmentViewState>(initialState = MainFragmentViewState()) {
}

data class MainFragmentViewState(override val isLoading: Boolean = false) : ViewState