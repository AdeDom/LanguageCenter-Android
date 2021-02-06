package com.lc.library.presentation.usecase

import com.lc.library.data.repository.Resource
import com.lc.server.models.response.FetchVocabularyDetailResponse

interface FetchVocabularyDetailUseCase {
    suspend operator fun invoke(vocabularyGroupId: Int): Resource<FetchVocabularyDetailResponse>
}
