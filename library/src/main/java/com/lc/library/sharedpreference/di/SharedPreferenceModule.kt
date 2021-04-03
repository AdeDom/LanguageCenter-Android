package com.lc.library.sharedpreference.di

import com.lc.library.sharedpreference.pref.AuthPref
import com.lc.library.sharedpreference.pref.AuthPrefImpl
import com.lc.library.sharedpreference.pref.ConfigPref
import com.lc.library.sharedpreference.pref.ConfigPrefImpl
import org.koin.dsl.module

val sharedPreferenceModule = module {

    single<AuthPref> { AuthPrefImpl(get()) }
    single<ConfigPref> { ConfigPrefImpl(get()) }

}
