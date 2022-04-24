package com.nikodem.adoptme.repositories

import android.content.SharedPreferences
import androidx.core.content.edit
import java.lang.RuntimeException

class NoTokenStoredException : RuntimeException()

interface TokenRepository {
    fun setTokens(accessToken: String, refreshToken: String)
    fun setSession(session: String)
    fun getSession(): String?
    fun getAccessToken(): String
    fun getRefreshToken(): String
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

    override fun getAccessToken(): String {
        return sharedPreferences.getString(ACCESS_TOKEN, null) ?: throw NoTokenStoredException()
    }

    override fun getRefreshToken(): String {
        return sharedPreferences.getString(REFRESH_TOKEN, null) ?: throw NoTokenStoredException()
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