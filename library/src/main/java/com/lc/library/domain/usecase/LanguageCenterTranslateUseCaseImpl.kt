package com.lc.library.domain.usecase

import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.LanguageCenterTranslateUseCase
import com.lc.server.models.response.LanguageCenterTranslateResponse

class LanguageCenterTranslateUseCaseImpl(
    private val repository: LanguageCenterRepository,
) : LanguageCenterTranslateUseCase {
    override suspend fun invoke(vocabulary: String?): Resource<LanguageCenterTranslateResponse> {
        return repository.callLanguageCenterTranslate(vocabulary)
    }
}
