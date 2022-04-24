package com.nikodem.adoptme.ui.profile_screen

import com.nikodem.adoptme.usecases.GetUserUseCase
import com.nikodem.adoptme.utils.BaseViewModel
import com.nikodem.adoptme.utils.ViewState
import com.nikodem.adoptme.utils.navigation.NavigationManager
import com.nikodem.adoptme.utils.navigation.NavigationManagerImpl

class ProfileScreenFragmentViewModel(
    private val navigationManager: NavigationManager = NavigationManagerImpl(),
    private val getUserUseCase: GetUserUseCase
) : BaseViewModel<ProfileScreenFragmentViewState>(initialState = ProfileScreenFragmentViewState()),
    NavigationManager by navigationManager {

    init {
        getUserData()
    }

    fun onBackButtonClicked() {
        navigateBack()
    }

    fun onLogoutClicked() {
        logout()
    }

    fun getUserData() {
        safeLaunch {
            val user = getUserUseCase.invoke()
            updateViewState {
                it.copy(
                    firstName = user.firstName,
                    lastName = user.lastName,
                    phoneNumber = user.phoneNumber,
                    email = user.email
                )
            }
        }
    }
}

data class ProfileScreenFragmentViewState(
    override val isLoading: Boolean = false,
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val email: String = ""
) : ViewState