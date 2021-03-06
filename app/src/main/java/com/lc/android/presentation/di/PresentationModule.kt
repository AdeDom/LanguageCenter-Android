package com.lc.android.presentation.di

import com.lc.android.presentation.addfriendgroupdetail.AddFriendGroupDetailViewModel
import com.lc.android.presentation.addvocabulary.AddVocabularyViewModel
import com.lc.android.presentation.chats.ChatsViewModel
import com.lc.android.presentation.community.CommunityViewModel
import com.lc.android.presentation.edit.localelearning.EditLocaleLearningViewModel
import com.lc.android.presentation.edit.localenative.EditLocaleNativeViewModel
import com.lc.android.presentation.edit.profile.EditProfileViewModel
import com.lc.android.presentation.friendgroup.FriendGroupViewModel
import com.lc.android.presentation.friendgroupdetail.FriendGroupDetailViewModel
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
import com.lc.android.presentation.vocabularydetail.VocabularyDetailViewModel
import com.lc.android.presentation.vocapbulary.VocabularyViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@FlowPreview
@ExperimentalCoroutinesApi
val presentationModule = module {

    viewModel { SplashScreenViewModel(get(), get()) }
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
    viewModel { MainViewModel(get(), get(), get()) }
    viewModel { TalkViewModel(get(), get(), get(), get(), get(), get(), get()) }
    viewModel { ChatsViewModel(get()) }
    viewModel { AddVocabularyViewModel(get()) }
    viewModel { VocabularyViewModel(get(), get(), get()) }
    viewModel { VocabularyDetailViewModel(get(), get()) }
    viewModel { FriendGroupViewModel(get(), get(), get(), get()) }
    viewModel { FriendGroupDetailViewModel(get(), get(), get()) }
    viewModel { AddFriendGroupDetailViewModel(get()) }

}
