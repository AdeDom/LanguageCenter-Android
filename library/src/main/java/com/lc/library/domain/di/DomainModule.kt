package com.lc.library.domain.di

import com.lc.library.domain.usecase.FetchUserInfoUseCaseImpl
import com.lc.library.domain.usecase.SignInUseCaseImpl
import com.lc.library.presentation.FetchUserInfoUseCase
import com.lc.library.presentation.SignInUseCase
import org.koin.dsl.module

private val domainModule = module {

    single<SignInUseCase> { SignInUseCaseImpl(get()) }
    single<FetchUserInfoUseCase> { FetchUserInfoUseCaseImpl(get()) }

}

val getDomainModule = domainModule
