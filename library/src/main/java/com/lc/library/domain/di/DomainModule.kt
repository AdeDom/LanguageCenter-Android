package com.lc.library.domain.di

import com.lc.library.domain.usecase.*
import com.lc.library.presentation.usecase.*
import org.koin.dsl.module

private val domainModule = module {

    single<SignInUseCase> { SignInUseCaseImpl(get()) }
    single<SignOutUseCase> { SignOutUseCaseImpl(get()) }
    single<GetUserInfoUseCase> { GetUserInfoUseCaseImpl(get()) }
    single<GuideUpdateProfileUseCase> { GuideUpdateProfileUseCaseImpl(get()) }
    single<EditUserInfoUseCase> { EditUserInfoUseCaseImpl(get()) }
    single<FetchCommunityUseCase> { FetchCommunityUseCaseImpl(get()) }
    single<AddAlgorithmUseCase> { AddAlgorithmUseCaseImpl(get()) }
    single<AddChatGroupNewUseCase> { AddChatGroupNewUseCaseImpl(get()) }
    single<AddChatGroupUseCase> { AddChatGroupUseCaseImpl(get()) }
    single<FetchChatGroupUseCase> { FetchChatGroupUseCaseImpl(get()) }
    single<FetchChatGroupDetailUseCase> { FetchChatGroupDetailUseCaseImpl(get()) }
    single<RemoveChatGroupUseCase> { RemoveChatGroupUseCaseImpl(get()) }
    single<RenameChatGroupUseCase> { RenameChatGroupUseCaseImpl(get()) }

}

val getDomainModule = domainModule
