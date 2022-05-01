package com.nikodem.adoptme.ui.end_logging_in

import com.hadilq.liveevent.LiveEvent
import com.nikodem.adoptme.usecases.VerifyPinUseCase
import com.nikodem.adoptme.utils.BaseViewModel
import com.nikodem.adoptme.utils.ViewState
import com.nikodem.adoptme.utils.fireEvent

class EndLoggingInFragmentViewModel(
    private val verifyPinUseCase: VerifyPinUseCase
) :
    BaseViewModel<EndLoggingInFragmentViewState>(initialState = EndLoggingInFragmentViewState()) {

    val navigateToFormFragmentEvent: LiveEvent<Unit> = LiveEvent()

    private fun isPin4Digit() {
        with(currentState) {
            updateViewState {
                it.copy(
                    isButtonEnabled = pin.length == 4,
                    isErrorVisible = pin.length == 4
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
                isPinValid = true
            )
        }
        with(currentState) {
            if (pin.isNotEmpty()) {
                updateViewState { it.copy(isPinValid = isPinValid(pin)) }
            }
        }
    }

    fun onRegisterButtonClick() {
        validate()
        if (currentState.isPinValid) {
            safeLaunch {
                updateViewState { it.copy(isLoading = true) }

                with(currentState) {
                    verifyPinUseCase.invoke(pin)
                }

                updateViewState { it.copy(isLoading = false) }

                navigateToFormFragmentEvent.fireEvent()
            }
        } else {
            showSnackbarEvent.fireEvent("Pin invalid")
        }
    }
}

data class EndLoggingInFragmentViewState(
    val pin: String = "",
    val isPinValid: Boolean = true,
    val errorText: String? = null,
    val isButtonEnabled: Boolean = false,
    val isErrorVisible: Boolean = false,
    override val isLoading: Boolean = false
) : ViewState