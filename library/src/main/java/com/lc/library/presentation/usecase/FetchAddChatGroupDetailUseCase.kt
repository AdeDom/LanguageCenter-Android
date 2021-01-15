package com.lc.library.presentation.usecase

import com.lc.library.data.repository.Resource
import com.lc.server.models.response.FetchAddChatGroupDetailResponse

interface FetchAddChatGroupDetailUseCase {
    suspend operator fun invoke(): Resource<FetchAddChatGroupDetailResponse>
}
