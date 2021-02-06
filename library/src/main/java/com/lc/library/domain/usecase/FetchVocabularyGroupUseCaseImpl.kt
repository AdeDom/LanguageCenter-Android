package com.lc.library.domain.usecase

import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.FetchVocabularyGroupUseCase
import com.lc.server.models.response.FetchVocabularyGroupResponse

class FetchVocabularyGroupUseCaseImpl(
    private val repository: LanguageCenterRepository,
) : FetchVocabularyGroupUseCase {
    override suspend fun invoke(): Resource<FetchVocabularyGroupResponse> {
        return repository.callFetchVocabularyGroup()
    }
}
