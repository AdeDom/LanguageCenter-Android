package com.lc.library.domain.usecase

import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.SignOutUseCase

class SignOutUseCaseImpl(private val repository: LanguageCenterRepository) : SignOutUseCase {
    override suspend fun invoke() {
        repository.signOut()
    }
}
