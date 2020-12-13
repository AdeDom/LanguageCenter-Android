package com.lc.library.domain.di

import com.lc.library.domain.usecase.SignInUseCaseImpl
import com.lc.library.presentation.SignInUseCase
import org.koin.dsl.module

private val domainModule = module {

    single<SignInUseCase> { SignInUseCaseImpl(get()) }

}

val getDomainModule = domainModule
