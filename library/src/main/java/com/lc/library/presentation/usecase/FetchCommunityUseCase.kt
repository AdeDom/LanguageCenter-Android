package com.lc.library.presentation.usecase

import com.lc.library.data.repository.Resource
import com.lc.server.models.model.Community
import com.lc.server.models.response.FetchCommunityResponse

interface FetchCommunityUseCase {
    suspend operator fun invoke(): Resource<FetchCommunityResponse>
    fun searchCommunity(communities: List<Community>, search: String): List<Community>
}
