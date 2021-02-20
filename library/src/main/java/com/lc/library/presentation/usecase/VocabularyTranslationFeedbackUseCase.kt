package com.lc.library.presentation.usecase

import com.lc.library.data.repository.Resource
import com.lc.server.models.request.VocabularyTranslationFeedbackRequest
import com.lc.server.models.response.BaseResponse

interface VocabularyTranslationFeedbackUseCase {
    suspend operator fun invoke(vocabularyTranslationFeedbackRequest: VocabularyTranslationFeedbackRequest): Resource<BaseResponse>
}
