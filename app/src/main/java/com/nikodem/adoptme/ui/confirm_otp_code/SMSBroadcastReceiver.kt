package com.nikodem.adoptme.ui.confirm_otp_code

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

class SMSBroadcastReceiver(
    private val onMessageReceived: ((String) -> Unit)? = null
): BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == SmsRetriever.SMS_RETRIEVED_ACTION) {
            val extras = intent.extras
            val status = extras?.get(SmsRetriever.EXTRA_STATUS) as Status?
            if (status?.statusCode == CommonStatusCodes.SUCCESS) {
                (extras?.get(SmsRetriever.EXTRA_SMS_MESSAGE) as String?)?.let {
                    onMessageReceived?.invoke(it)
                }
            }
        }
    }
}