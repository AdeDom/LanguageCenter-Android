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
    single<AddChatGroupUseCase> { AddChatGroupUseCaseImpl(get()) }
    single<FetchChatGroupUseCase> { FetchChatGroupUseCaseImpl(get()) }
    single<FetchChatGroupDetailUseCase> { FetchChatGroupDetailUseCaseImpl(get()) }
    single<RemoveChatGroupUseCase> { RemoveChatGroupUseCaseImpl(get()) }
    single<RenameChatGroupUseCase> { RenameChatGroupUseCaseImpl(get()) }
    single<GetAddChatGroupDetailUseCase> { GetAddChatGroupDetailUseCaseImpl(get(), get()) }
    single<ChangeChatGroupUseCase> { ChangeChatGroupUseCaseImpl(get()) }
    single<RemoveChatGroupDetailUseCase> { RemoveChatGroupDetailUseCaseImpl(get()) }
    single<FetchFriendInfoUseCase> { FetchFriendInfoUseCaseImpl(get()) }
    single<AddChatGroupFriendUseCase> { AddChatGroupFriendUseCaseImpl(get()) }
    single<SendMessageUseCase> { SendMessageUseCaseImpl(get()) }
    single<GetTalkUseCase> { GetTalkUseCaseImpl(get()) }
    single<GetChatListUseCase> { GetChatListUseCaseImpl(get()) }
    single<ReadMessagesUseCase> { ReadMessagesUseCaseImpl(get()) }
    single<SaveChatListUseCase> { SaveChatListUseCaseImpl(get()) }
    single<ResendMessageUseCase> { ResendMessageUseCaseImpl(get(), get()) }
    single<FetchTalkUnreceivedUseCase> { FetchTalkUnreceivedUseCaseImpl(get()) }
    single<TalkWebSocketsUseCase> { TalkWebSocketsUseCaseImpl(get()) }
    single<FilePrefUseCase> { FilePrefUseCaseImpl(get(), get()) }
    single<LanguageCenterTranslateUseCase> { LanguageCenterTranslateUseCaseImpl(get()) }
    single<AddVocabularyTranslationUseCase> { AddVocabularyTranslationUseCaseImpl(get()) }
    single<FetchVocabularyGroupUseCase> { FetchVocabularyGroupUseCaseImpl(get()) }
    single<FetchVocabularyDetailUseCase> { FetchVocabularyDetailUseCaseImpl(get()) }
    single<ValidateTokenUseCase> { ValidateTokenUseCaseImpl(get()) }
    single<GetVocabularyFeedbackUseCase> { GetVocabularyFeedbackUseCaseImpl(get()) }

}

val getDomainModule = domainModule
