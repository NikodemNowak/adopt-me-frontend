package com.nikodem.adoptme.ui.home_screen.details_screen

import com.nikodem.adoptme.utils.BaseViewModel
import com.nikodem.adoptme.utils.ViewState
import com.nikodem.adoptme.utils.navigation.NavigationManager
import com.nikodem.adoptme.utils.navigation.NavigationManagerImpl

class DetailsScreenFragmentViewModel(
    val navigationManager: NavigationManager = NavigationManagerImpl()
) : BaseViewModel<DetailsScreenFragmentViewState>(initialState = DetailsScreenFragmentViewState()),
    NavigationManager by navigationManager {

    fun onBackButtonClicked() {
        navigateBack()
    }
}

data class DetailsScreenFragmentViewState(override val isLoading: Boolean = false) : ViewState