package com.lc.library.presentation.usecase

import com.lc.library.data.repository.Resource
import com.lc.server.models.response.FetchTalkUnreceivedResponse

interface FetchTalkUnreceivedUseCase {
    suspend operator fun invoke(): Resource<FetchTalkUnreceivedResponse>
}
