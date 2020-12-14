package com.lc.android.presentation.di

import com.lc.android.presentation.main.MainViewModel
import com.lc.android.presentation.signin.SignInViewModel
import com.lc.android.presentation.splashscreen.SplashScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val presentationModule = module {

    viewModel { SplashScreenViewModel(get()) }
    viewModel { SignInViewModel(get()) }
    viewModel { MainViewModel(get(), get()) }

}

val getPresentationModule = presentationModule
