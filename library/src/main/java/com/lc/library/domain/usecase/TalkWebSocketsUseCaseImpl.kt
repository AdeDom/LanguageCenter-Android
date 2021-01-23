package com.lc.library.domain.usecase

import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.TalkWebSocketsUseCase

class TalkWebSocketsUseCaseImpl(
    private val repository: LanguageCenterRepository,
) : TalkWebSocketsUseCase {
    override suspend fun invoke() {
        repository.incomingSendMessageSocket()
    }
}
