package com.lc.library.domain.di

import com.lc.library.domain.usecase.GetUserInfoUseCaseImpl
import com.lc.library.domain.usecase.SignInUseCaseImpl
import com.lc.library.domain.usecase.SignOutUseCaseImpl
import com.lc.library.presentation.usecase.GetUserInfoUseCase
import com.lc.library.presentation.usecase.SignInUseCase
import com.lc.library.presentation.usecase.SignOutUseCase
import org.koin.dsl.module

private val domainModule = module {

    single<SignInUseCase> { SignInUseCaseImpl(get()) }
    single<SignOutUseCase> { SignOutUseCaseImpl(get()) }
    single<GetUserInfoUseCase> { GetUserInfoUseCaseImpl(get(), get()) }

}

val getDomainModule = domainModule
