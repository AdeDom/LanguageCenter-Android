package com.lc.library.domain.usecase

import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.VocabularyTranslationFeedbackUseCase
import com.lc.server.models.request.VocabularyTranslationFeedbackRequest
import com.lc.server.models.response.BaseResponse

class VocabularyTranslationFeedbackUseCaseImpl(
    private val repository: LanguageCenterRepository,
) : VocabularyTranslationFeedbackUseCase {
    override suspend fun invoke(vocabularyTranslationFeedbackRequest: VocabularyTranslationFeedbackRequest): Resource<BaseResponse> {
        return repository.callVocabularyTranslationFeedback(vocabularyTranslationFeedbackRequest)
    }
}
