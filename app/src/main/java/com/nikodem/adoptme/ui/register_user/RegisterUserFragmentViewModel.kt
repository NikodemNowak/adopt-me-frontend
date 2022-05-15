package com.nikodem.adoptme.ui.register_user

import android.util.Patterns
import androidx.lifecycle.SavedStateHandle
import com.hadilq.liveevent.LiveEvent
import com.nikodem.adoptme.usecases.AddUserUseCase
import com.nikodem.adoptme.usecases.User
import com.nikodem.adoptme.utils.AdoptMeError
import com.nikodem.adoptme.utils.BaseViewModel
import com.nikodem.adoptme.utils.ViewState
import com.nikodem.adoptme.utils.fireEvent

class RegisterUserFragmentViewModel(
    private val addUserUseCase: AddUserUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<RegisterUserFragmentViewState>(initialState = RegisterUserFragmentViewState()) {

    val navigateToOtpCodeFragmentEvent: LiveEvent<Unit> = LiveEvent()

    fun onFirstNameChange(firstName: String) {
        updateViewState {
            it.copy(
                firstName = firstName
            )
        }
    }

    fun onLastNameChange(lastName: String) {
        updateViewState {
            it.copy(
                lastName = lastName
            )
        }
    }

    fun onCityChange(city: String) {
        updateViewState {
            it.copy(
                city = city
            )
        }
    }

    fun onPhoneNumberChange(phoneNumber: String) {
        updateViewState {
            it.copy(
                phoneNumber = phoneNumber
            )
        }
    }

    fun onEmailChange(email: String) {
        updateViewState {
            it.copy(
                email = email
            )
        }
    }

    fun validate() {
        with(viewState.value!!) {
            updateViewState {
                it.copy(
                    isFormValid = true
                )
            }
            if (firstName.isEmpty()) updateViewState {
                it.copy(
                    firstNameError = "First name cannot be empty",
                    isFormValid = false
                )
            } else updateViewState { it.copy(firstNameError = null) }

            if (lastName.isEmpty()) updateViewState {
                it.copy(
                    lastNameError = "Last name cannot be empty",
                    isFormValid = false
                )
            } else updateViewState { it.copy(lastNameError = null) }

            if (city.isEmpty()) updateViewState {
                it.copy(
                    cityError = "City cannot be empty",
                    isFormValid = false
                )
            } else updateViewState { it.copy(cityError = null) }

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

            if (email.isEmpty()) updateViewState {
                it.copy(
                    emailError = "Email cannot be empty",
                    isFormValid = false
                )
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) updateViewState {
                it.copy(
                    emailError = "Invalid email (example@example.com)",
                    isFormValid = false
                )
            }
            else updateViewState { it.copy(emailError = null) }
        }
    }

    fun sendUser() {
        validate()
        if (viewState.value!!.isFormValid) {
            safeLaunch {
                updateViewState { it.copy(isLoading = true) }

                with(viewState.value!!) {
                    addUserUseCase.invoke(
                        User(
                            firstName, lastName, city, phoneNumber, email, null
                        )
                    )
                }

                updateViewState { it.copy(isLoading = false) }

                navigateToOtpCodeFragmentEvent.fireEvent()
            }
        } else {
            showSnackbarEvent.fireEvent("Invalid data")
        }
    }

    override fun handleError(throwable: Throwable, retryAction: () -> Unit) {
        updateViewState { it.copy(isLoading = false) }
        when (throwable) {
            is AdoptMeError.EmailTakenError -> {
                showToastEvent.fireEvent("Email is already taken")
            }
            is AdoptMeError.PhoneTakenError -> {
                showToastEvent.fireEvent("Phone number is already taken")
            }
            else -> {
                super.handleError(throwable, retryAction)
            }
        }
    }
}

data class RegisterUserFragmentViewState(
    override val isLoading: Boolean = false,
    val firstName: String = "",
    val firstNameError: String? = null,
    val lastName: String = "",
    val lastNameError: String? = null,
    val city: String = "",
    val cityError: String? = null,
    val phoneNumber: String = "",
    val phoneNumberError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val isFormValid: Boolean = true
) : ViewState