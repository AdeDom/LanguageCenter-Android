package com.lc.library.presentation.usecase

import com.lc.library.data.repository.Resource
import com.lc.server.models.response.FetchVocabularyGroupResponse

interface FetchVocabularyGroupUseCase {
    suspend operator fun invoke(): Resource<FetchVocabularyGroupResponse>
}
