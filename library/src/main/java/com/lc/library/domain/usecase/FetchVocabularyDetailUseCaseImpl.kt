package com.lc.library.domain.usecase

import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.FetchVocabularyDetailUseCase
import com.lc.server.models.response.FetchVocabularyDetailResponse

class FetchVocabularyDetailUseCaseImpl(
    private val repository: LanguageCenterRepository,
) : FetchVocabularyDetailUseCase {
    override suspend fun invoke(vocabularyGroupId: Int): Resource<FetchVocabularyDetailResponse> {
        return repository.callFetchVocabularyDetail(vocabularyGroupId)
    }
}
