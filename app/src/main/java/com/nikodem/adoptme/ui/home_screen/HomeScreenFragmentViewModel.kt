package com.nikodem.adoptme.ui.home_screen

import com.nikodem.adoptme.utils.BaseViewModel
import com.nikodem.adoptme.utils.ViewState

class HomeScreenFragmentViewModel :
    BaseViewModel<HomeScreenFragmentViewState>(initialState = HomeScreenFragmentViewState()) {
}

data class HomeScreenFragmentViewState(override val isLoading: Boolean = false) : ViewState