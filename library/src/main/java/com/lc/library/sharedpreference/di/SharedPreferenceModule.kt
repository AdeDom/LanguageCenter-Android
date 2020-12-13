package com.lc.library.sharedpreference.di

import com.lc.library.sharedpreference.pref.PreferenceAuth
import com.lc.library.sharedpreference.pref.PreferenceAuthImpl
import org.koin.dsl.module

private val sharedPreferenceModule = module {

    single<PreferenceAuth> { PreferenceAuthImpl(get()) }

}

val getSharedPreferenceModule = sharedPreferenceModule
