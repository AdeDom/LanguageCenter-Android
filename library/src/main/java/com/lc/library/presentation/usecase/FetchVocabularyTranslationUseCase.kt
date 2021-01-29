package com.lc.library.presentation.usecase

import com.lc.library.data.repository.Resource
import com.lc.server.models.response.FetchVocabularyTranslationResponse

interface FetchVocabularyTranslationUseCase {
    suspend operator fun invoke(): Resource<FetchVocabularyTranslationResponse>
}
