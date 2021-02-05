package com.lc.library.domain.usecase

import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.AddVocabularyTranslationUseCase
import com.lc.server.models.request.AddVocabularyTranslationRequest
import com.lc.server.models.response.BaseResponse

class AddVocabularyTranslationUseCaseImpl(
    private val repository: LanguageCenterRepository,
) : AddVocabularyTranslationUseCase {
    override suspend fun invoke(addVocabularyTranslationRequest: AddVocabularyTranslationRequest): Resource<BaseResponse> {
        return repository.callAddVocabularyTranslation(addVocabularyTranslationRequest)
    }
}
