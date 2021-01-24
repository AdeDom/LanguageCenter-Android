package com.lc.android.presentation.di

import com.lc.android.presentation.addchatgroupdetail.AddChatGroupDetailViewModel
import com.lc.android.presentation.chatgroup.ChatGroupViewModel
import com.lc.android.presentation.chatgroupdetail.ChatGroupDetailViewModel
import com.lc.android.presentation.chats.ChatsViewModel
import com.lc.android.presentation.community.CommunityViewModel
import com.lc.android.presentation.edit.localelearning.EditLocaleLearningViewModel
import com.lc.android.presentation.edit.localenative.EditLocaleNativeViewModel
import com.lc.android.presentation.edit.profile.EditProfileViewModel
import com.lc.android.presentation.guide.birthdate.GuideBirthDateViewModel
import com.lc.android.presentation.guide.gender.GuideGenderViewModel
import com.lc.android.presentation.guide.learninglanguage.GuideLearningLanguageViewModel
import com.lc.android.presentation.guide.nativelanguage.GuideNativeLanguageViewModel
import com.lc.android.presentation.main.MainViewModel
import com.lc.android.presentation.profile.ProfileViewModel
import com.lc.android.presentation.signin.SignInViewModel
import com.lc.android.presentation.splashscreen.SplashScreenViewModel
import com.lc.android.presentation.talk.TalkViewModel
import com.lc.android.presentation.userinfo.UserInfoViewModel
import io.ktor.util.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@FlowPreview
@KtorExperimentalAPI
@ExperimentalCoroutinesApi
private val presentationModule = module {

    viewModel { SplashScreenViewModel(get()) }
    viewModel { SignInViewModel(get(), get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { GuideGenderViewModel(get()) }
    viewModel { GuideBirthDateViewModel(get(), get()) }
    viewModel { GuideNativeLanguageViewModel(get()) }
    viewModel { GuideLearningLanguageViewModel(get()) }
    viewModel { EditProfileViewModel(get(), get()) }
    viewModel { EditLocaleNativeViewModel(get(), get()) }
    viewModel { EditLocaleLearningViewModel(get(), get()) }
    viewModel { CommunityViewModel(get()) }
    viewModel { UserInfoViewModel(get(), get(), get()) }
    viewModel { ChatGroupViewModel(get(), get(), get(), get()) }
    viewModel { ChatGroupDetailViewModel(get(), get(), get()) }
    viewModel { AddChatGroupDetailViewModel(get()) }
    viewModel { MainViewModel(get(), get(), get()) }
    viewModel { TalkViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { ChatsViewModel(get()) }

}

@FlowPreview
@KtorExperimentalAPI
@ExperimentalCoroutinesApi
val getPresentationModule = presentationModule
