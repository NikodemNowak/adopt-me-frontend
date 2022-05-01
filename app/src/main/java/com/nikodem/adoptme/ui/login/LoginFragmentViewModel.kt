package com.nikodem.adoptme.ui.login

import com.hadilq.liveevent.LiveEvent
import com.nikodem.adoptme.usecases.SendOtpUseCase
import com.nikodem.adoptme.usecases.User
import com.nikodem.adoptme.usecases.UserLogin
import com.nikodem.adoptme.utils.BaseViewModel
import com.nikodem.adoptme.utils.ViewState
import com.nikodem.adoptme.utils.fireEvent

class LoginFragmentViewModel(
    private val sendOtpUseCase: SendOtpUseCase
) :
    BaseViewModel<LoginFragmentViewState>(initialState = LoginFragmentViewState()) {

    val navigateToVerifyOtpCodeFragment: LiveEvent<Unit> = LiveEvent()

    fun onPhoneNumberChange(phoneNumber: String) {
        updateViewState { it.copy(phoneNumber = phoneNumber) }
    }

    fun validate() {
        with(viewState.value!!) {
            updateViewState { it.copy(isFormValid = true) }
            if (phoneNumber.isEmpty()) updateViewState {
                it.copy(
                    phoneNumberError = "Phone number cannot be empty",
                    isFormValid = false
                )
            }
            if (!phoneNumber.matches(Regex("^\\+48\\d{9}\$"))) updateViewState {
                it.copy(
                    phoneNumberError = "Incorrect phone number (+XX XXX XXX XXX)",
                    isFormValid = false
                )
            } else updateViewState { it.copy(phoneNumberError = null) }
        }
    }

    fun sendPhoneNumber() {
        validate()
        if (viewState.value!!.isFormValid) {
            safeLaunch {
                updateViewState { it.copy(isLoading = true) }

                with(viewState.value!!) {
                    sendOtpUseCase.invoke(
                        UserLogin(phoneNumber)
                    )
                }

                updateViewState { it.copy(isLoading = false) }

                navigateToVerifyOtpCodeFragment.fireEvent()
            }
        } else {
            showSnackbarEvent.fireEvent("Invalid data")
        }

    }
}

data class LoginFragmentViewState(
    override val isLoading: Boolean = false,
    val phoneNumber: String = "",
    val phoneNumberError: String? = null,
    val isFormValid: Boolean = true
) : ViewState