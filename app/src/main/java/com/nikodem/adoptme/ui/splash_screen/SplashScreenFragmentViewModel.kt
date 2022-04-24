package com.nikodem.adoptme.ui.splash_screen

import com.nikodem.adoptme.ui.splash_screen.SplashScreenFragmentDirections.Companion.toHomeScreenFragment
import com.nikodem.adoptme.ui.splash_screen.SplashScreenFragmentDirections.Companion.toLoginOrRegisterFragment
import com.nikodem.adoptme.usecases.IsLoggedInUseCase
import com.nikodem.adoptme.utils.BaseViewModel
import com.nikodem.adoptme.utils.ViewState
import com.nikodem.adoptme.utils.navigation.NavigationManager
import com.nikodem.adoptme.utils.navigation.NavigationManagerImpl
import kotlinx.coroutines.delay

class SplashScreenFragmentViewModel(
    private val navigationManager: NavigationManager = NavigationManagerImpl(),
    private val isLoggedInUseCase: IsLoggedInUseCase
) : BaseViewModel<SplashScreenFragmentViewState>(
    SplashScreenFragmentViewState()
), NavigationManager by navigationManager {

    fun onViewInitialized() {
        safeLaunch {
            delay(500)
            if (isLoggedInUseCase.invoke()) {
                navigateTo(toHomeScreenFragment())
            } else {
                navigateTo(toLoginOrRegisterFragment())
            }
        }
    }
}

data class SplashScreenFragmentViewState(override val isLoading: Boolean = false) : ViewState