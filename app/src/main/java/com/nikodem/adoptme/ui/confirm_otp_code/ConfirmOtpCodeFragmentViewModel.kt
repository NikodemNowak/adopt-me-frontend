package com.nikodem.adoptme.ui.confirm_otp_code

import com.nikodem.adoptme.utils.BaseViewModel
import com.nikodem.adoptme.utils.ViewState

class ConfirmOtpCodeFragmentViewModel :
    BaseViewModel<ConfirmOtpCodeFragmentViewState>(initialState = ConfirmOtpCodeFragmentViewState()) {
}

data class ConfirmOtpCodeFragmentViewState(
    val timer: Int = 30,
    override val isLoading: Boolean = false
) : ViewState