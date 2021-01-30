package com.lc.library.sharedpreference.pref

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class ConfigPrefImpl(context: Context) : ConfigPref {

    private val sharedPreference: SharedPreferences =
        context.getSharedPreferences(ConfigPref.CONFIG_PREF, Context.MODE_PRIVATE)

    override var selectPage: String
        get() = sharedPreference
            .getString(ConfigPref.SELECT_PAGE_KEY, ConfigPref.SELECT_PAGE_COMMUNITY)
            ?: ConfigPref.SELECT_PAGE_COMMUNITY
        set(value) {
            sharedPreference.edit {
                putString(ConfigPref.SELECT_PAGE_KEY, value)
            }
        }

    override var isTranslateThToEn: Boolean
        get() = sharedPreference.getBoolean(ConfigPref.TRANSLATE_KEY, true)
        set(value) {
            sharedPreference.edit {
                putBoolean(ConfigPref.TRANSLATE_KEY, value)
            }
        }

}
