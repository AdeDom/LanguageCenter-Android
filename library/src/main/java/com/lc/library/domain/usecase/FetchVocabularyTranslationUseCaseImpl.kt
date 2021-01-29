package com.lc.library.domain.usecase

import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.FetchVocabularyTranslationUseCase
import com.lc.server.models.response.FetchVocabularyTranslationResponse

class FetchVocabularyTranslationUseCaseImpl(
    private val repository: LanguageCenterRepository,
) : FetchVocabularyTranslationUseCase {
    override suspend fun invoke(): Resource<FetchVocabularyTranslationResponse> {
        return repository.callFetchVocabularyTranslation()
    }
}
