package com.lc.library.sharedpreference.pref

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class AuthPrefImpl(context: Context) : AuthPref {

    private val sharedPreference: SharedPreferences =
        context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)

    override var accessToken: String
        get() = sharedPreference.getString(ACCESS_TOKEN, "").orEmpty()
        set(value) {
            sharedPreference.edit {
                putString(ACCESS_TOKEN, value)
            }
        }

    override var refreshToken: String
        get() = sharedPreference.getString(REFRESH_TOKEN, "").orEmpty()
        set(value) {
            sharedPreference.edit {
                putString(REFRESH_TOKEN, value)
            }
        }

    companion object {
        const val PREF_FILE = "pref_auth"
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
    }

}
