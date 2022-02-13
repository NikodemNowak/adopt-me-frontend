package com.nikodem.adoptme.ui.confirm_otp_code

import android.content.IntentFilter
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.nikodem.adoptme.R
import com.nikodem.adoptme.databinding.FragmentConfirmOtpCodeBinding
import com.nikodem.adoptme.ui.dialog.CancelRegistrationDialog
import com.nikodem.adoptme.ui.register_user.RegisterUserFragmentDirections
import com.nikodem.adoptme.utils.BaseFragment
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber

class ConfirmOtpCodeFragment :
    BaseFragment<ConfirmOtpCodeFragmentViewState, ConfirmOtpCodeFragmentViewModel, FragmentConfirmOtpCodeBinding>(
        R.layout.fragment_confirm_otp_code, ConfirmOtpCodeFragmentViewModel::class
    ) {
    private val smsHandler: SmsHandler by inject()
    private var smsBroadcastReceiver: SMSBroadcastReceiver? = null

    private val args: ConfirmOtpCodeFragmentArgs by navArgs()

    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onArgsReceived(args.authProcess)

        Timber.d("Creating SMSBroadcastReceiver")
        smsBroadcastReceiver = SMSBroadcastReceiver { message ->
            Timber.d("Otp message received")
            viewModel.onSmsReceived(message.extractOtp())
        }

        lifecycleScope.launch {
            Timber.d("Initializing smsHandler")
            smsHandler.initialize()
            requireContext().registerReceiver(
                smsBroadcastReceiver,
                IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
            )
        }

//        viewModel.restoreState()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.restoreState()

        onBackEvent = {
        }

        binding.otpCode.doOnTextChanged { text, start, before, count ->
            viewModel.onOtpCodeChange(text.toString())
        }

        var counter = 30
        val countTime: TextView = binding.timer
        timer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.resend.isEnabled = false
                countTime.text = counter.toString()
                counter--
            }

            override fun onFinish() {
                countTime.text = "0"
                counter = 30
                binding.resend.isEnabled = true
            }
        }
        timer?.start()

        binding.button.setOnClickListener {
            viewModel.sendOtpCode()
        }

        viewModel.navigateToEndRegistration.observe(viewLifecycleOwner) {
            findNavController().navigate(ConfirmOtpCodeFragmentDirections.actionConfirmOtpCodeFragmentToEndRegistrationFragment())

        }
        viewModel.navigateToEndLoggingIn.observe(viewLifecycleOwner) {
            findNavController().navigate(ConfirmOtpCodeFragmentDirections.actionConfirmOtpCodeFragmentToEndLoggingInFragment())

        }

        binding.resend.setOnClickListener {
            timer?.start()
            Toast.makeText(context, "Code has been resent", Toast.LENGTH_SHORT).show()
            //viewModel.resendCode()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
//        viewModel.saveState()
    }

    override fun onDestroyView() {
        timer?.cancel()
        lifecycleScope.launch {
            smsBroadcastReceiver?.let { requireContext().unregisterReceiver(it) }
        }
        super.onDestroyView()
    }

    fun String.extractOtp(): String? {
        return OTP_REGEX.find(this)?.value
    }

    companion object {
        val OTP_REGEX = "[0-9]{6}".toRegex()
    }
}