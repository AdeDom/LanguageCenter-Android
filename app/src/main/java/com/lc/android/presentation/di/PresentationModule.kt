package com.lc.android.presentation.di

import com.lc.android.presentation.guide.birthdate.GuideBirthDateViewModel
import com.lc.android.presentation.guide.gender.GuideGenderViewModel
import com.lc.android.presentation.guide.learninglanguage.GuideLearningLanguageViewModel
import com.lc.android.presentation.guide.nativelanguage.GuideNativeLanguageViewModel
import com.lc.android.presentation.profile.ProfileViewModel
import com.lc.android.presentation.signin.SignInViewModel
import com.lc.android.presentation.splashscreen.SplashScreenViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@FlowPreview
@ExperimentalCoroutinesApi
private val presentationModule = module {

    viewModel { SplashScreenViewModel(get()) }
    viewModel { SignInViewModel(get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { GuideGenderViewModel() }
    viewModel { GuideBirthDateViewModel(get()) }
    viewModel { GuideNativeLanguageViewModel(get()) }
    viewModel { GuideLearningLanguageViewModel(get()) }

}

@FlowPreview
@ExperimentalCoroutinesApi
val getPresentationModule = presentationModule
