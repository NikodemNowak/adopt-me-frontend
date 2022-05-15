package com.nikodem.adoptme.ui.end_registration

import com.hadilq.liveevent.LiveEvent
import com.nikodem.adoptme.usecases.SetPinUseCase
import com.nikodem.adoptme.usecases.User
import com.nikodem.adoptme.utils.BaseViewModel
import com.nikodem.adoptme.utils.ViewState
import com.nikodem.adoptme.utils.fireEvent
import timber.log.Timber

class EndRegistrationFragmentViewModel(
    private val setPinUseCase: SetPinUseCase
) :
    BaseViewModel<EndRegistrationFragmentViewState>(initialState = EndRegistrationFragmentViewState()) {

    val navigateToFormFragmentEvent: LiveEvent<Unit> = LiveEvent()
    val navigateToHomeScreenFragmentEvent: LiveEvent<Unit> = LiveEvent()

    private fun isPin4Digit() {
        with(currentState) {
            updateViewState {
                it.copy(
                    isButtonEnabled = pin.length == 4 && confirmPin.length == 4 && pin == confirmPin,
                    isErrorVisible = pin.length == 4 && confirmPin.length == 4 && pin != confirmPin
                )
            }
        }
    }

    fun onPinChange(pin: String) {
        updateViewState {
            it.copy(
                pin = pin
            )
        }
        isPin4Digit()
    }

    fun onConfirmPinChange(confirmPin: String) {
        updateViewState {
            it.copy(
                confirmPin = confirmPin
            )
        }
        isPin4Digit()
    }

//    1111
//    1234
//
//    1195 - zle, bo 2x 1 obok siebie
//    1719 - tak jest ok
//    1259, 2670, 9651 - nie może być, bo cyfry po sobie
//    2468 - ok

    private fun isPinValid(pin: String): Boolean {
        var lastDigit = -1
        for (c in pin) {
            val digit = c.digitToInt()

            lastDigit = if (lastDigit == -1) {
                digit
            } else if (digit == lastDigit || digit == lastDigit + 1 || digit == lastDigit - 1) {
                return false
            } else {
                digit
            }
        }

        return true
    }

    fun validate() {
        updateViewState {
            it.copy(
                arePinsValid = true
            )
        }
        with(currentState) {
            if (pin.isNotEmpty() && confirmPin.isNotEmpty()) {
                updateViewState { it.copy(arePinsValid = isPinValid(pin)) }
            }
        }
    }

    fun onRegisterButtonClick() {
        validate()
        if (currentState.arePinsValid) {
            safeLaunch {
                updateViewState { it.copy(isLoading = true) }

                with(currentState) {
                    setPinUseCase.invoke(pin)
                }

                updateViewState { it.copy(isLoading = false) }

                navigateToHomeScreenFragmentEvent.fireEvent()
            }
        } else {
            showSnackbarEvent.fireEvent("Pin invalid")
        }
    }
}

data class EndRegistrationFragmentViewState(
    val pin: String = "",
    val confirmPin: String = "",
    val errorText: String? = null,
    val arePinsValid: Boolean = true,
    val isButtonEnabled: Boolean = false,
    val isErrorVisible: Boolean = false,
    override val isLoading: Boolean = false
) : ViewState