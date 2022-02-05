package com.nikodem.adoptme.repositories

import android.content.SharedPreferences
import androidx.core.content.edit

interface TokenRepository {
    fun setTokens(accessToken: String, refreshToken: String)
    fun setSession(session: String)
    fun getSession(): String?
}

class TokenRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : TokenRepository, Cache {
    override fun setTokens(accessToken: String, refreshToken: String) {
        sharedPreferences.edit {
            putString(ACCESS_TOKEN, accessToken)
            putString(REFRESH_TOKEN, refreshToken)
        }
    }

    override fun setSession(session: String) {
        sharedPreferences.edit {
            putString(SESSION, session)
        }
    }

    override fun getSession(): String? {
        return sharedPreferences.getString(SESSION, "")
    }

    override fun invalidate() {
        sharedPreferences.edit {
            remove(ACCESS_TOKEN)
            remove(REFRESH_TOKEN)
            remove(SESSION)
        }
    }

    companion object {
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
        const val REFRESH_TOKEN = "REFRESH_TOKEN"
        const val SESSION = "SESSION"
    }
}