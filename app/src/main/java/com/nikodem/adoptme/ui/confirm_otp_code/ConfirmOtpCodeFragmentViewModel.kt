package com.nikodem.adoptme.ui.confirm_otp_code

import androidx.lifecycle.SavedStateHandle
import com.hadilq.liveevent.LiveEvent
import com.nikodem.adoptme.usecases.VerifyLoginOtpUseCase
import com.nikodem.adoptme.usecases.VerifyRegisterOtpUseCase
import com.nikodem.adoptme.utils.BaseViewModel
import com.nikodem.adoptme.utils.ViewState
import com.nikodem.adoptme.utils.fireEvent

class ConfirmOtpCodeFragmentViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val verifyRegisterOtpUseCase: VerifyRegisterOtpUseCase,
    private val verifyLoginOtpUseCase: VerifyLoginOtpUseCase
) : BaseViewModel<ConfirmOtpCodeFragmentViewState>(initialState = ConfirmOtpCodeFragmentViewState()) {

    val navigateToEndRegistration: LiveEvent<Unit> = LiveEvent()
    val navigateToEndLoggingIn: LiveEvent<Unit> = LiveEvent()

//    fun saveState() {
//        savedStateHandle.set("TIMER_VALUE", currentState.timer)
//        savedStateHandle.set("COSTAM_KEY", Costam(x = 10))
//    }
//
//    fun restoreState() {
//        val timerValue = savedStateHandle.get<Int>("TIMER_VALUE")
//        val costam = savedStateHandle.get<Costam>("COSTAM_KEY")
//    }

    fun onArgsReceived(authProcess: AuthProcess) {
        updateViewState {
            it.copy(
                authProcess = authProcess
            )
        }
    }

    fun onOtpCodeChange(otpCode: String) {
        updateViewState {
            it.copy(
                otpCode = otpCode
            )
        }
    }

    fun onSmsReceived(otp: String?) {
        otp?.let {
            updateViewState {
                it.copy(
                    otpCode = otp
                )
            }

            sendOtpCode()
        }
    }

    fun sendOtpCode() {
        safeLaunch {
            updateViewState { it.copy(isLoading = true) }

            if (viewState.value!!.authProcess == AuthProcess.REGISTER) {
                verifyRegisterOtpUseCase.invoke(viewState.value!!.otpCode)
            } else {
                verifyLoginOtpUseCase.invoke(viewState.value!!.otpCode)
            }

            updateViewState { it.copy(isLoading = false) }

            if (viewState.value!!.authProcess == AuthProcess.REGISTER) {
                navigateToEndRegistration.fireEvent()
            } else {
                navigateToEndLoggingIn.fireEvent()
            }
        }
    }
}

data class ConfirmOtpCodeFragmentViewState(
    val timer: Int = 30,
    val otpCode: String = "",
    val authProcess: AuthProcess? = null,
    override val isLoading: Boolean = false
) : ViewState