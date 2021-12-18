package com.nikodem.adoptme.ui.favorites

import com.nikodem.adoptme.utils.BaseViewModel
import com.nikodem.adoptme.utils.ViewState

class FavoritesFragmentViewModel :
    BaseViewModel<FavoritesFragmentViewState>(initialState = FavoritesFragmentViewState()) {
}

data class FavoritesFragmentViewState(override val isLoading: Boolean = false) : ViewState