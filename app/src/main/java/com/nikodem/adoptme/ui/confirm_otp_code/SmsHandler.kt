package com.nikodem.adoptme.ui.confirm_otp_code

import android.content.Context
import androidx.core.content.contentValuesOf
import com.google.android.gms.auth.api.phone.SmsRetriever
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface SmsHandler {
    suspend fun initialize()
}

interface SmsClient {
    suspend fun initialize()
}

class SmsClientImpl(
    private val context: Context
): SmsClient {
    override suspend fun initialize() {
        suspendCoroutine<Void> { continuation ->
            SmsRetriever.getClient(context).startSmsRetriever()
                .addOnCompleteListener {
                    continuation.resume(it.result)
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }
}

class SmsHandlerImpl(
    private val client: SmsClient
): SmsHandler {
    override suspend fun initialize() {
        client.initialize()
    }
}

