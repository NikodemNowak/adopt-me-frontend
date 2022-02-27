package com.nikodem.adoptme.ui.home_screen

import com.nikodem.adoptme.utils.BaseViewModel
import com.nikodem.adoptme.utils.ViewState
import com.nikodem.adoptme.utils.navigation.NavigationManager
import com.nikodem.adoptme.utils.navigation.NavigationManagerImpl

class HomeScreenFragmentViewModel :
    BaseViewModel<HomeScreenFragmentViewState>(
        initialState = HomeScreenFragmentViewState()
    ), NavigationManager by NavigationManagerImpl() {

    fun onButtonClick() {
        updateViewState { it.copy(isLoading = !it.isLoading) }
    }

    fun onItemClicked() {
        navigateTo(HomeScreenFragmentDirections.toDetailsScreenFragment())
    }
}

data class HomeScreenFragmentViewState(override val isLoading: Boolean = false) : ViewState