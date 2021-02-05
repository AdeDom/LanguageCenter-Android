package com.lc.library.presentation.usecase

import com.lc.library.data.repository.Resource
import com.lc.server.models.request.AddVocabularyTranslationRequest
import com.lc.server.models.response.BaseResponse

interface AddVocabularyTranslationUseCase {
    suspend operator fun invoke(addVocabularyTranslationRequest: AddVocabularyTranslationRequest): Resource<BaseResponse>
}
