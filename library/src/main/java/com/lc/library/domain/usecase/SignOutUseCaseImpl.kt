package com.lc.library.domain.usecase

import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.SignOutUseCase

class SignOutUseCaseImpl(private val repository: LanguageCenterRepository) : SignOutUseCase {
    override suspend fun invoke() {
        repository.signOut()
    }
}
